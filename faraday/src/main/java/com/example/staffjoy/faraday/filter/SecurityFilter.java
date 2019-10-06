package com.example.staffjoy.faraday.filter;

import com.example.commonlib.commonlib.envconfig.EnvConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

import static org.springframework.util.StringUtils.isEmpty;


/**
 * debug环境http
 * 其他环境https
 */
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {
    private EnvConfig envConfig;

    public SecurityFilter(EnvConfig envConfig){
        this.envConfig = envConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String origin = httpServletRequest.getHeader("Origin");
        if(!isEmpty(origin)){
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,DELETE,PUT,OPTIONS");
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"Accept, Content-Type, Content-Length, Cookie, Accept-Encoding, X-CSRF-Token, Authorization");
        }
        if("OPTIONS".equals(httpServletRequest.getMethod())){
            return;
        }

        if(!this.envConfig.isDebug()){
            boolean isSecure = httpServletRequest.isSecure();
            if(!isSecure){
                if("https".equals(httpServletRequest.getHeader("X-Forwarded-Proto"))){
                    isSecure = true;
                }
            }
            /**
             * 是转发请求
             */
            if(!isSecure){
                log.info("redirect to https");
                try {
                    URI redirect = new URI("https", httpServletRequest.getServerName(), httpServletRequest.getRequestURI());
                    httpServletResponse.sendRedirect(redirect.toString());
                }catch (Exception e){
                    log.error("fail to build redirect url", e);
                }
            }
            /**
             * https请求
             */
            httpServletResponse.setHeader("Strict-Transport-Security","max-age=315360000; includeSubDomains; preload");
            httpServletResponse.setHeader("X-Frame-Options", "DENY");
            httpServletResponse.setHeader("X-XSS-Protection", "1; mode=block");
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
