package com.example.staffjoy.faraday.trace;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IncomRequest extends  HttpEntity{
    private String host;
    private String uri;
    private String method;


}
