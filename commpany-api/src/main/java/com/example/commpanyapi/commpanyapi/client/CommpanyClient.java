package com.example.commpanyapi.commpanyapi.client;

import com.example.commonlib.commonlib.auth.AuthConstant;
import com.example.commpanyapi.commpanyapi.dto.CommpanyDto;
import com.example.commpanyapi.commpanyapi.dto.CommpanyListReponse;
import com.example.commpanyapi.commpanyapi.dto.GenericCompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class CommpanyClient {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${staffjoy.company-service-endpoint}")
    private String url;
    private String path = "/v1/company";
    public GenericCompanyResponse createComapny(String auth, @Valid CommpanyDto commpanyDto)
            throws URISyntaxException {
        URI uri = new URI(url+path+"/create");
        RequestEntity requestEntity = createRequestEntityPost(uri,auth,commpanyDto);
        ResponseEntity<GenericCompanyResponse> responseResponseEntity =
        restTemplate.exchange(requestEntity,GenericCompanyResponse.class);
        return responseResponseEntity.getBody();
    }

    public CommpanyListReponse listCompanies(String auth,int offset,int limit){
        URI uri = UriComponentsBuilder.fromUriString(url+path+"/list?" +
                "offset={offset}&limit={limit}").build(offset,limit);
        RequestEntity requestEntity = createRequestEntiryGet(uri,auth);
        ResponseEntity<CommpanyListReponse> reponseResponseEntity=
                restTemplate.exchange(requestEntity
                ,CommpanyListReponse.class);
        return reponseResponseEntity.getBody();

    }

    public GenericCompanyResponse getCompany(String auth,String commpanyId){
        URI uri = UriComponentsBuilder.fromUriString(url+path+"/get?commpanyId={commpanyId}")
                .build(commpanyId);
        RequestEntity<Void> requestEntity = createRequestEntiryGet(uri,auth);
        ResponseEntity<GenericCompanyResponse> responseResponseEntity =
                restTemplate.exchange(requestEntity,GenericCompanyResponse.class);
        return responseResponseEntity.getBody();
    }

    RequestEntity createRequestEntityPost(URI uri,String auth,Object obj){
        HttpHeaders headers = new HttpHeaders();
        headers.add(AuthConstant.AUTHORIZATION_HEADER,auth);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(obj);
        RequestEntity<MappingJacksonValue> requestEntity = new
                RequestEntity<>(mappingJacksonValue,
                headers, HttpMethod.POST,uri);
        return requestEntity;
    }

    RequestEntity createRequestEntiryGet(URI uri,String auth){
        HttpHeaders headers = new HttpHeaders();
        headers.add(AuthConstant.AUTHORIZATION_HEADER,auth);
        RequestEntity<Void> requestEntity = new RequestEntity<Void>
                (headers,HttpMethod.GET,uri);
        return requestEntity;
    }

}
