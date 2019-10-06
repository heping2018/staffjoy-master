package com.example.staffjoy.faraday.intercepter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.commonlib.commonlib.auth.AuthConstant;
import com.example.commonlib.commonlib.auth.Sessions;
import com.example.commonlib.commonlib.crypto.Sign;
import com.example.staffjoy.faraday.config.MappingPropertise;
import com.example.staffjoy.faraday.http.RequestData;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class AuthRequestInterceptor implements PreForwardRequestInterceptor {

    private final String signingSecret;

    public AuthRequestInterceptor(String signingSecret) {
        this.signingSecret = signingSecret;
    }


    @Override
    public void intercepter(RequestData requestData, MappingPropertise mappingPropertise) {
        //默认为未经过验证的用户
        String faradayAnonymous = AuthConstant.AUTHORIZATION_ANONYMOUS_WEB;
        HttpHeaders headers = requestData.getHttpHeaders();
        Session session = getSession(requestData.getHttpServletRequest());
        if(session != null){
            if(session.isSupport()){
                faradayAnonymous = AuthConstant.AUTHORIZATION_SUPPORT_USER;
            }else {
                faradayAnonymous = AuthConstant.AUTHORIZATION_AUTHENTICATED_USER;
            }
        }else {
            headers.remove(AuthConstant.CURRENT_USER_HEADER);

        }
        headers.set(AuthConstant.AUTHORIZATION_HEADER,faradayAnonymous);

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
