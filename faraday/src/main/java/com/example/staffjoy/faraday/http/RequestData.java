package com.example.staffjoy.faraday.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

@Setter
@Getter
public class RequestData extends UnmodifiableRequestData{
    private boolean needReirect;
    private String redirectUrl;
    public RequestData(HttpMethod method, String uri, String host, HttpHeaders httpHeaders, byte[] body, HttpServletRequest httpServletRequest) {
        super(method, uri, host, httpHeaders, body, httpServletRequest);
    }
}
