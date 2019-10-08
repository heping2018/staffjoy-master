package com.example.staffjoy.faraday.trace;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
public class HttpEntity {
    private HttpHeaders httpHeaders;
}
