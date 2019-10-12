package com.example.staffjoy.faraday.http;


import com.example.staffjoy.faraday.balancer.Balancer;
import com.example.staffjoy.faraday.config.FaradayPropertise;
import com.example.staffjoy.faraday.config.MappingPropertise;
import com.example.staffjoy.faraday.exception.FaradayException;
import com.example.staffjoy.faraday.trace.ProxyTraceIntercepter;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Optional;

import static java.lang.System.nanoTime;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.ResponseEntity.status;

/**
 * 请求到达网关后发送到后置服务
 */
public class RequstForwarder {


    private static final ILogger log = SLoggerFactory.getLogger(RequstForwarder.class);

    private FaradayPropertise faradayPropertise;

    private Optional<MeterRegistry> meterRegistry;

    private Balancer balancer;

    private ProxyTraceIntercepter proxyTraceIntercepter;

    private HttpProvide httpProvide;

    public RequstForwarder(FaradayPropertise faradayPropertise,Optional<MeterRegistry> meterRegistry
            ,Balancer balancer,HttpProvide httpProvide
    ,ProxyTraceIntercepter proxyTraceIntercepter){
        this.faradayPropertise = faradayPropertise;
        this.meterRegistry = meterRegistry;
        this.balancer = balancer;
        this.proxyTraceIntercepter = proxyTraceIntercepter;
        this.httpProvide = httpProvide;
    }




    public ResponseEntity<byte[]> forwardDestination(RequestData data, String traceId,
                                                     MappingPropertise mappingPropertise){
        String metername = resolverMetername(mappingPropertise);
        ForwardDestiantion forwardDestiantion = resloverForwarDestiantion(data,mappingPropertise,metername);
        prepareForwardedRequestHeaders(data);
        //链路跟踪
        proxyTraceIntercepter.onForWardStart(traceId,data.getHost(),
                data.getUri(),data.getMethod().toString(),data.getHttpHeaders(),
                mappingPropertise.getName(),data.getBody());
        //rest转发准备
        RequestEntity<byte[]> requestEntity = new RequestEntity<byte[]>
                (data.getBody(),data.getHttpHeaders(),data.getMethod(),forwardDestiantion.getUri());
        ResponseData responseData = sendRequest(traceId,metername,requestEntity,mappingPropertise,data);
        log.debug(String.format("Forwarded: %s %s %s -> %s %d", data.getMethod(), data.getHost()
                , data.getUri(), forwardDestiantion.getUri(), responseData.getStatus().value()));
        proxyTraceIntercepter.onForwardComplete(traceId,responseData.getHttpHeaders(),responseData.getBody(),String.valueOf(responseData.getStatus().value()));
        prepareForwardedResponseHeaders(responseData);
        return status(responseData.getStatus()).headers(responseData.getHttpHeaders()).body(responseData.getBody());
    }

    private void prepareForwardedResponseHeaders(ResponseData responseData){
        HttpHeaders headers = responseData.getHttpHeaders();
        headers.remove(TRANSFER_ENCODING);
        headers.remove(CONNECTION);
        headers.remove("Public-Key-Pins");
        headers.remove(SERVER);
        headers.remove("Strict-Transport-Security");
    }


    private ResponseData sendRequest(String traceId, String metricName, RequestEntity<byte[]> requestEntity, MappingPropertise mapping
    , RequestData requestData){
        long starttime = nanoTime();
        ResponseEntity<byte[]> response;
        try {
            response= httpProvide.getHttpMap().get(mapping.getName()).exchange(requestEntity,byte[].class);
            LencyRecodeTime(metricName,starttime);
        }catch (HttpStatusCodeException e){
            LencyRecodeTime(metricName,starttime);
            response = status(e.getStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsByteArray());
        }catch (Exception e){
            LencyRecodeTime(metricName,starttime);
            proxyTraceIntercepter.onForwardError(traceId,e);
            throw e;
        }
        UnmodifiableRequestData unmodifiableRequestData =
                new UnmodifiableRequestData(requestData.getMethod(),requestData.getUri(),requestData.getHost(),requestData.getHttpHeaders()
                ,requestData.getBody(),requestData.getHttpServletRequest());
        return new ResponseData(response.getBody(),unmodifiableRequestData,response.getStatusCode(),response.getHeaders());
    }


    private ForwardDestiantion resloverForwarDestiantion(RequestData requestData,MappingPropertise mapping,String metername){
        return new ForwardDestiantion(createDesitionUrl(requestData.getUri(),mapping),mapping.getName(),metername);

    }

    private void LencyRecodeTime(String metricName,long startTime){
        meterRegistry.ifPresent(meterRegistry -> {
            meterRegistry.timer(metricName).record(Duration.ofNanos(nanoTime()-startTime));
        });
    }

    private String resolverMetername(MappingPropertise mappingPropertise){
        return faradayPropertise.getMeterPropertise().getPrefix()+"."+mappingPropertise.getName();
    }

    private URI createDesitionUrl(String uri,MappingPropertise mappingPropertise){
        String balanceUri = balancer.chooseDesetination(mappingPropertise.getDestinations());
        try {
            URI uri2 = new URI(uri + balanceUri);
            return uri2;
        } catch (URISyntaxException e) {
           throw new FaradayException("Error createing destination from Http Request");
        }
    }

    private void prepareForwardedRequestHeaders(RequestData data){
        HttpHeaders headers = data.getHttpHeaders();
        headers.remove(HttpHeaders.TE);
    }
}
