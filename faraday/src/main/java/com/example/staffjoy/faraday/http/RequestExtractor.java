package com.example.staffjoy.faraday.http;


import com.example.staffjoy.faraday.exception.FaradayException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.apache.commons.io.IOUtils.toByteArray;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class RequestExtractor {

    public HttpHeaders extractorHttpHeaders(HttpServletRequest httpServletRequest){
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headersName = httpServletRequest.getHeaderNames();
        while (headersName.hasMoreElements()){
            String name = headersName.nextElement();
            List list = Collections.list(httpServletRequest.getHeaders(name));
            headers.put(name,list);
        }
        return headers;
    }


    public byte[] extraBody(HttpServletRequest httpServletRequest){
        try {
          return   toByteArray(httpServletRequest.getInputStream());
        } catch (IOException e) {
            throw new FaradayException("Error extracting body of HTTP request with URI: "
                    + extractrorUri(httpServletRequest), e);
        }
    }


    public HttpMethod extractorHttpMethod(HttpServletRequest httpServletRequest){
        return HttpMethod.resolve(httpServletRequest.getMethod());
    }


    public String extractorHost(HttpServletRequest httpServletRequest){
        return httpServletRequest.getServerName();
    }


    public String extractrorUri(HttpServletRequest httpRequest){
        return httpRequest.getRequestURL().append(getQueue(httpRequest)).toString();
    }

    private String getQueue(HttpServletRequest httpRequest){
        return httpRequest.getQueryString() == null ? EMPTY : "?" + httpRequest.getQueryString();
    }
}
