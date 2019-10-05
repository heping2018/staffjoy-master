package com.example.staffjoy.faraday.http;

import com.example.staffjoy.faraday.config.MappingPropertise;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toMap;

import static org.apache.http.impl.client.HttpClientBuilder.create;

public class HttpProvide {

    private Map<String,RestTemplate> httpMap = new HashMap<String,RestTemplate>();

    /**
     * name ：restTemplat 映射
     * @param mappingPropertiseList
     */
    public void updateHttpClient(List<MappingPropertise> mappingPropertiseList){
        mappingPropertiseList.stream().collect(toMap(MappingPropertise :: getName,this :: createRestTemplat));
    }

    private RestTemplate createRestTemplat(MappingPropertise mappingPropertise){
        CloseableHttpClient httpClient = createHttpClient().build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(mappingPropertise.getTimeOutPropertise().getConnect());
        requestFactory.setReadTimeout(mappingPropertise.getTimeOutPropertise().getRead());
        return new RestTemplate(requestFactory);
    }

    private HttpClientBuilder createHttpClient(){
        return create().useSystemProperties().disableRedirectHandling().disableCookieManagement();
    }
}
