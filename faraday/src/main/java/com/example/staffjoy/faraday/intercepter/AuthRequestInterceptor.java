package com.example.staffjoy.faraday.intercepter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.commonlib.commonlib.auth.AuthConstant;
import com.example.commonlib.commonlib.auth.Sessions;
import com.example.commonlib.commonlib.crypto.Sign;
import com.example.commonlib.commonlib.envconfig.EnvConfig;
import com.example.commonlib.commonlib.service.SecurityConstant;
import com.example.commonlib.commonlib.service.Service;
import com.example.commonlib.commonlib.service.ServiceDirectory;
import com.example.staffjoy.faraday.config.MappingPropertise;
import com.example.staffjoy.faraday.exception.FaradayException;
import com.example.staffjoy.faraday.http.RequestData;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * 对微服务之间的调用进行检查
 */
@Slf4j
public class AuthRequestInterceptor implements PreForwardRequestInterceptor {

    private final String signingSecret;
    private EnvConfig envConfig;


    public AuthRequestInterceptor(String signingSecret,EnvConfig envConfig) {
        this.signingSecret = signingSecret;
        this.envConfig = envConfig;
    }


    @Override
    public void intercepter(RequestData requestData, MappingPropertise mappingPropertise) {
        String faradayAnonymous = this.setAuthHeader(requestData);
        this.validateRestrict(mappingPropertise);
        this.validataSecurity(faradayAnonymous,mappingPropertise,requestData);
    }

    /**
     * 先验证跳转到NakeDomianFilter
     * 完成后跳转到原请求服务
     * @param faradayAnonymous
     * @param mappingPropertise
     * @param data
     */
    private void validataSecurity(String faradayAnonymous,MappingPropertise mappingPropertise,RequestData data){
        if(AuthConstant.AUTHORIZATION_ANONYMOUS_WEB.equals(faradayAnonymous)){
            Service service = this.getService(mappingPropertise);
            if(SecurityConstant.SEC_PUBLIC != service.getSecurity()){
                log.info("annoymous user want to access secure service,redict to login");
                String scheme = "http";
                if(!envConfig.isDebug()){
                    scheme = "https";
                }
                int port = data.getHttpServletRequest().getServerPort();
                try {
                    URI redirct = new URI(scheme,null,"www."+envConfig.getExternalApex(),port,"login",null,null);
                    String returnInfo = data.getHost()+data.getUri();
                    String fullRedirectUrl = redirct.toString() + "?return_to="+returnInfo;
                    data.setUri(fullRedirectUrl);
                    data.setNeedReirect(true);
                } catch (URISyntaxException e) {
                    log.error("Fail to build redirect url", e);
                }

            }
        }
    }

    private void validateRestrict(MappingPropertise mappingPropertise){
        Service service = this.getService(mappingPropertise);
        if(service.isRestrictDev() && envConfig.isDebug() ){
            throw new FaradayException("This service is restrict to dev and test environment only");
        }
    }

    private Service getService(MappingPropertise mappingPropertise){
        Map<String,Service> map = ServiceDirectory.getMap();
        String host  = mappingPropertise.getHost();
        String faradayAnonymous = host.replace("."+envConfig.getExternalApex(),"");
        Service service = map.get(faradayAnonymous);
        if(service == null){
            throw  new FaradayException("Unsupported sub-domain " + faradayAnonymous);
        }
        return service;

    }

    private String setAuthHeader(RequestData requestData){
        //默认为未经过验证的用户
        String faradayAnonymous = AuthConstant.AUTHORIZATION_ANONYMOUS_WEB;
        HttpHeaders headers = requestData.getHttpHeaders();
        Session session = this.getSession(requestData.getHttpServletRequest());
        if(session != null){
            if(session.isSupport()){
                faradayAnonymous = AuthConstant.AUTHORIZATION_SUPPORT_USER;
            }else {
                faradayAnonymous = AuthConstant.AUTHORIZATION_AUTHENTICATED_USER;
            }

            headers.set(AuthConstant.CURRENT_USER_HEADER,session.userId);
        }else {
            headers.remove(AuthConstant.CURRENT_USER_HEADER);

        }
        headers.set(AuthConstant.AUTHORIZATION_HEADER,faradayAnonymous);
        return faradayAnonymous;
    }


    private Session getSession(HttpServletRequest httpRequest){
        String token = Sessions.getToken(httpRequest);
        if(token == null) return null;
        try {
            DecodedJWT decodedJWT = Sign.verifierToken(token, signingSecret);
            String userId = decodedJWT.getClaim(Sign.CLAIM_USER_ID).asString();
            boolean support = decodedJWT.getClaim(Sign.CLAIM_SUPPORT).asBoolean();
            Session session = Session.builder().support(support).userId(userId).build();
            return session;
        }catch (Exception e){
            log.error("fail to verify token", "token", token, e);
            return null;
        }


    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @Data
    @Builder
    private static class Session{
        private String userId;
        private boolean support;
    }
}
