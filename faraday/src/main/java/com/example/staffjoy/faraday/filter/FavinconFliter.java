package com.example.staffjoy.faraday.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FavinconFliter extends OncePerRequestFilter {

    private static final String healthurl = "/health";

    private byte[] favinconBytes;

    public FavinconFliter(byte[] favinconBytes){
        this.favinconBytes = favinconBytes;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(healthurl.equals(httpServletRequest.getRequestURI())){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.getWriter().println(favinconBytes);
        }else{
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }

    }
}
