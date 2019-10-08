package com.example.staffjoy.faraday.trace;

import com.example.staffjoy.faraday.config.FaradayPropertise;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

public class ProxyTraceIntercepter {
    private TraceInterceptor traceInterceptor;
    private FaradayPropertise faradayPropertise;

    public ProxyTraceIntercepter(TraceInterceptor traceInterceptor,FaradayPropertise faradayPropertise){
        this.traceInterceptor = traceInterceptor;
        this.faradayPropertise = faradayPropertise;
    }

    public String getUUID(){
        return faradayPropertise.getTracing().isEnable()? String.valueOf(UUID.randomUUID()) :null;
    }

    private void runifTracingEnable(Runnable runnable){
        if(faradayPropertise.getTracing().isEnable()){
            runnable.run();
        }
    }

    public IncomRequest getIncomRequest(String host, String uri, String method, HttpHeaders httpHeaders){
        IncomRequest incomRequest = new IncomRequest();
        incomRequest.setHost(host);
        incomRequest.setHost(uri);
        incomRequest.setHost(method);
        incomRequest.setHttpHeaders(httpHeaders);
        return incomRequest;
    }
    public void onRequestRecive(String tracId,String host,String uri,String method,HttpHeaders httpHeaders){
        runifTracingEnable(() -> {
            IncomRequest incomRequest = getIncomRequest(host,uri,method,httpHeaders);
            traceInterceptor.onRequestRecive(tracId,incomRequest);
        });
    }
    public void onNoMappingFound (String tracId,String host,String uri,String method,HttpHeaders httpHeaders){
        runifTracingEnable(() -> {
            IncomRequest incomRequest = getIncomRequest(host,uri,method,httpHeaders);
            traceInterceptor.onNoMappingFound(tracId,incomRequest);
        });
    }

    public void onForWardStart(String traceId, String host,String uri,String method,HttpHeaders httpHeaders,String mapping,byte[] body) {
        runifTracingEnable(() -> {
            ForwordRequest forwordRequest = new ForwordRequest();
            forwordRequest.setBody(body);
            forwordRequest.setMappingName(mapping);
            forwordRequest.setHost(host);
            forwordRequest.setHttpHeaders(httpHeaders);
            forwordRequest.setMethod(method);
            forwordRequest.setUri(uri);
            traceInterceptor.onForWardStart(traceId,forwordRequest);

        });
    }
    public void onForwardError(String traceId,Throwable throwable){
        runifTracingEnable(() -> {
            traceInterceptor.onForwardError(traceId,throwable);
        });
    }

    public void onForwardComplete(String traceId,HttpHeaders httpHeaders,byte[] body,String status){
        runifTracingEnable(()->{
            ReceivedResponse receivedResponse = new ReceivedResponse();
            receivedResponse.setBody(body);
            receivedResponse.setStatus(status);
            receivedResponse.setHttpHeaders(httpHeaders);
            traceInterceptor.onForwardComplete(traceId,receivedResponse);
        });
    }
}
