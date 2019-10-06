package com.example.staffjoy.faraday.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UnmodifiableRequestData {
    protected HttpMethod method;
    protected String uri;
    protected String host;
    protected HttpHeaders httpHeaders;
    protected byte[] body;
    protected HttpServletRequest httpServletRequest;
}
