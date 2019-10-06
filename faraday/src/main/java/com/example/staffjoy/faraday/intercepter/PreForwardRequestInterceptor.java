package com.example.staffjoy.faraday.intercepter;

import com.example.staffjoy.faraday.config.MappingPropertise;
import com.example.staffjoy.faraday.http.RequestData;

public interface PreForwardRequestInterceptor {
    public void intercepter(RequestData requestData, MappingPropertise mappingPropertise);
}
