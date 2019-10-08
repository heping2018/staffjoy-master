package com.example.staffjoy.faraday.trace;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForwordRequest extends IncomRequest {
    private String mappingName;
    private byte[] body;
}
