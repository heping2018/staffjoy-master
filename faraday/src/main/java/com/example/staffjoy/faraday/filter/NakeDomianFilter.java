package com.example.staffjoy.faraday.filter;

import com.example.commonlib.commonlib.envconfig.EnvConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 登录请求
 */
@Slf4j
public class NakeDomianFilter  extends OncePerRequestFilter {

    private static final String DEFAULT_SERVICE = "www";
    private EnvConfig envConfig;

    public NakeDomianFilter(EnvConfig envConfig){
        this.envConfig = envConfig;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(envConfig.getExternalApex().equals(httpServletRequest.getServerName())){
            log.info("hitting naked domain - redirect to www");
            String scheme = "http";
            if(!envConfig.isDebug()){
                scheme = "htpps";
            }
            try {
                URI redirectUrl = new URI(scheme,null,DEFAULT_SERVICE
                        + envConfig.getExternalApex(),httpServletRequest.getServerPort(),
                        "/login",null,null);
                httpServletResponse.sendRedirect(redirectUrl.toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
