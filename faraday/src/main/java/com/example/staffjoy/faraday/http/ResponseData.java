package com.example.staffjoy.faraday.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private byte[] body;
    private UnmodifiableRequestData unmodifiableRequestData;
    private HttpStatus status;
    private HttpHeaders httpHeaders;
}
