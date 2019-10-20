package com.example.accountapi.demo.client;

import com.example.accountapi.demo.AccountConstant;
import com.example.accountapi.demo.model.*;
import com.example.commonlib.commonlib.api.BaseResponse;
import com.example.commonlib.commonlib.auth.AuthConstant;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class AccontClientService {
    @Autowired
    private RestTemplate restTemplate;
    private String SERVICE_NAME = AccountConstant.SERVICE_NAME;
    private String AUTHORIZATION_HEADER = AuthConstant.AUTHORIZATION_HEADER;
    @Value("${staffjoy.account-service-endpoint}")
    private String url;
    private final String path = "/v1/account";

    public GenericAccountResponse createAcount(String auth,@Valid CreateAccountRequest request) throws URISyntaxException {
        URI uri = new URI(url+path+"/create");
        RequestEntity<MappingJacksonValue> requestEntity = creatHeaderRequstPost(uri,auth,request);
        ResponseEntity<GenericAccountResponse> responseResponseEntity = restTemplate.exchange(requestEntity,GenericAccountResponse.class);
        return responseResponseEntity.getBody();
    };

    public BaseResponse trackEvent(@Valid TrackEventRequest trackEventRequest){
        String uri = url+path+"/track_event";
        BaseResponse  baseResponse = restTemplate.postForObject(uri,trackEventRequest,BaseResponse.class);
        return baseResponse;
    }

    public BaseResponse syncUser(@Valid SyncUserRequest syncUserRequest){
        String uri = url+path+"/track_event";
        BaseResponse baseResponse = restTemplate.postForObject(uri,syncUserRequest,BaseResponse.class);
        return baseResponse;
    }
    //是get请求 因为是requestparam
    public ListAccountResponse listAccounts(String auth,int offset,@Min(0) int limit){
        URI uri = UriComponentsBuilder.fromUriString(url+path+"/create?offset={offset}&limit={limit}").build(offset,limit);
        RequestEntity<Void> requestEntity = createHeaderRequestGet(uri,auth);
        ResponseEntity<ListAccountResponse> listAccountResponse = restTemplate.exchange(requestEntity,ListAccountResponse.class);
        return listAccountResponse.getBody();
    }

    public GenericAccountResponse getOrCreateAccount(String auth,
                                                    @Valid GetOrCreatequest getOrCreatequest) throws URISyntaxException {
        URI uri = new URI(url+path+"/get_or_create");
        RequestEntity<MappingJacksonValue> requestEntity = creatHeaderRequstPost(uri,auth,getOrCreatequest);
        ResponseEntity<GenericAccountResponse> genericAccountResponseResponseEntity =
                restTemplate.exchange(requestEntity,GenericAccountResponse.class);
        return genericAccountResponseResponseEntity.getBody();

    }

    public GenericAccountResponse getAccount(String auth,
                                             @NotBlank String usrId){
        URI uri = UriComponentsBuilder.fromUriString(url+path+"/get?usrId={usrId}").build(usrId);
        RequestEntity<Void> requestEntity = createHeaderRequestGet(uri,auth);
        ResponseEntity<GenericAccountResponse> responseResponseEntity = restTemplate.exchange(requestEntity,GenericAccountResponse.class);
        return responseResponseEntity.getBody();

    }
    public GenericAccountResponse updateAccount(String auth, @Valid AccountDto accountDto ) throws URISyntaxException {
        URI uri = new URI(url+path+"/update");
        RequestEntity<MappingJacksonValue> accountDtoRequestEntity = creatHeaderRequstPost(uri,auth,accountDto);
        ResponseEntity<GenericAccountResponse> responseResponseEntity = restTemplate.exchange(accountDtoRequestEntity,GenericAccountResponse.class);
        return responseResponseEntity.getBody();
    }

    public GenericAccountResponse getAccountByphoneNumber( String auth, @Valid String phoneNumber){
        URI uri = UriComponentsBuilder.fromUriString(url+path+"/get_account_by_phonenumber?phoneNumber={phoneNumber}")
                .build(phoneNumber);
        RequestEntity<Void> requestEntity = createHeaderRequestGet(uri,auth);
        ResponseEntity<GenericAccountResponse> responseResponseEntity = restTemplate.exchange(requestEntity,GenericAccountResponse.class);
         return responseResponseEntity.getBody();
    }


    public BaseResponse updatePassword( String auth, @Valid UpdatePasswordRequest updatePasswordRequest) throws URISyntaxException {
        URI uri = new URI(url+path+"/update_password");
        RequestEntity<MappingJacksonValue> requestRequestEntity = creatHeaderRequstPost(uri,auth,updatePasswordRequest);
        ResponseEntity<BaseResponse> responseResponseEntity = restTemplate.exchange(requestRequestEntity,BaseResponse.class);
        return responseResponseEntity.getBody();
    }

    public BaseResponse verifyPassword(String auth, @Valid VerifyPasswordRequest verifyPasswordRequest) throws URISyntaxException {
        URI uri = new URI(url+path+"/verify_password");
        RequestEntity<MappingJacksonValue> requestRequestEntity = creatHeaderRequstPost(uri,auth,verifyPasswordRequest);
        ResponseEntity<BaseResponse> responseResponseEntity = restTemplate.exchange(requestRequestEntity,BaseResponse.class);
        return responseResponseEntity.getBody();
    }

    public BaseResponse requestPasswordReset(String auth,@Valid PasswordResetRequest resetRequest) throws URISyntaxException {
        URI uri = new URI(url+path+"/request_password_reset");
        RequestEntity<MappingJacksonValue> requestEntity = creatHeaderRequstPost(uri,auth,resetRequest);
        ResponseEntity<BaseResponse> responseResponseEntity = restTemplate.exchange(requestEntity,BaseResponse.class);
        return responseResponseEntity.getBody();
    }

    public BaseResponse requestEmailChange(String auth,@Valid EmailChangeRequest emailChangeRequest) throws URISyntaxException {
        URI uri = new URI(url+path+"/request_email_change");
        RequestEntity<MappingJacksonValue> requestEntity = creatHeaderRequstPost(uri,auth,emailChangeRequest);
        ResponseEntity<BaseResponse> responseResponseEntity = restTemplate.exchange(requestEntity,BaseResponse.class);
        return responseResponseEntity.getBody();
    }

    public BaseResponse changeEmail(String auth, EmailConfirmation emailConfirmation) throws URISyntaxException {
        URI uri = new URI(url+path+"/change_email");
        RequestEntity<MappingJacksonValue> requestEntity = creatHeaderRequstPost(uri,auth,emailConfirmation);
        ResponseEntity<BaseResponse> responseResponseEntity = restTemplate.exchange(requestEntity,BaseResponse.class);
        return responseResponseEntity.getBody();
    }

    private RequestEntity creatHeaderRequstPost(URI uri,String auth,Object requst){
        HttpHeaders headers = new HttpHeaders();
        headers.add(AuthConstant.AUTHORIZATION_HEADER,auth);
        MappingJacksonValue value = new MappingJacksonValue(requst);
        RequestEntity<MappingJacksonValue> requestEntity =
                new RequestEntity<MappingJacksonValue>(value,headers,HttpMethod.POST,uri);
        return requestEntity;
    }

    private RequestEntity<Void> createHeaderRequestGet(URI uri,String auth){
        HttpHeaders headers = new HttpHeaders();
        headers.add(AuthConstant.AUTHORIZATION_HEADER,auth);
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers,HttpMethod.GET,uri);
        return requestEntity;
    }



}
