package com.example.staffjoy.faraday.http;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

/**
 * 转发请求类
 */
@AllArgsConstructor
@Setter
@Getter
public class ForwardDestiantion {
    private final URI uri;
    private final String mappingname;
    private final String mappingMetername;


}
