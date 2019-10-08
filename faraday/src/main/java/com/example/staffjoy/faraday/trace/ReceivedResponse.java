package com.example.staffjoy.faraday.trace;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReceivedResponse extends HttpEntity {

    //返回状态
    private String status;
    //返回内容
    private byte[] body;
}
