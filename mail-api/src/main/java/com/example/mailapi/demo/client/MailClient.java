package com.example.mailapi.demo.client;

import com.example.commonlib.commonlib.api.BaseResponse;
import com.example.mailapi.demo.MailConstant;
import com.example.mailapi.demo.dto.EmaillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class MailClient {
    @Autowired
   private RestTemplate restTemplate;

    private final String path = "/v1";

    BaseResponse send(EmaillRequest emaillRequest){

        String uri = MailConstant.SERVICE_NAME+path;
        BaseResponse baseResponse = restTemplate.postForObject(uri,emaillRequest,BaseResponse.class);
        return baseResponse;
    }
}
