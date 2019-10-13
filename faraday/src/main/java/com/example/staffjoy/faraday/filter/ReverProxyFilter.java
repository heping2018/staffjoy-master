package com.example.staffjoy.faraday.filter;

import com.example.staffjoy.faraday.config.MappingPropertise;
import com.example.staffjoy.faraday.http.RequestData;
import com.example.staffjoy.faraday.http.RequestExtractor;
import com.example.staffjoy.faraday.http.RequstForwarder;
import com.example.staffjoy.faraday.intercepter.PreForwardRequestInterceptor;
import com.example.staffjoy.faraday.mapping.MappingProvider;
import com.example.staffjoy.faraday.trace.ProxyTraceIntercepter;
import com.example.staffjoy.faraday.trace.TraceInterceptor;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ReverProxyFilter extends OncePerRequestFilter {

    ILogger logger = SLoggerFactory.getLogger(ReverProxyFilter.class);

    protected static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";
    protected static final String X_FORWARDER_PROTO_HEADER = "X-Forwarded-Proto";
    protected static final String X_FORWARDED_HOST_HEADER = "X-Forwarded-Host";
    protected static final String X_FORWARDER_PORT_HEADER = "X-Fprwarded-port";


    private RequestExtractor requestExtractor;
    private ProxyTraceIntercepter traceInterceptor;
    private MappingProvider mappingProvider;
    private PreForwardRequestInterceptor preForwardRequestInterceptor;
    private RequstForwarder requstForwarder;


    public ReverProxyFilter(RequestExtractor requestExtractor,ProxyTraceIntercepter traceInterceptor,
                            MappingProvider mappingProvider,PreForwardRequestInterceptor preForwardRequestInterceptor,
                            RequstForwarder requstForwarder
    ){
        this.requestExtractor = requestExtractor;
        this.traceInterceptor = traceInterceptor;
        this.mappingProvider = mappingProvider;
        this.preForwardRequestInterceptor = preForwardRequestInterceptor;
        this.requestExtractor = requestExtractor;
        this.requstForwarder = requstForwarder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String serverName = requestExtractor.extractorHost(httpServletRequest);
        String uri = requestExtractor.extractrorUri(httpServletRequest);

        logger.debug("Income request ", "method",httpServletRequest.getMethod()
                ,"host",serverName,"uri",uri);
        HttpHeaders headers = requestExtractor.extractorHttpHeaders(httpServletRequest);
        HttpMethod httpMethod = requestExtractor.extractorHttpMethod(httpServletRequest);
        String traceId = traceInterceptor.getUUID();
        traceInterceptor.onRequestRecive(traceId,serverName,uri,httpMethod.toString(),headers);
        MappingPropertise mappingPropertise = mappingProvider.resolverMapping(serverName,httpServletRequest);
        if(mappingPropertise == null){
            traceInterceptor.onNoMappingFound(traceId,serverName,uri,httpMethod.toString(),headers);
            logger.debug(String.format("Forwarding: %s %s %s -> no mapping found", httpMethod, serverName, uri));
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.getWriter().println("Unsupported domain");
            return;
        }else{
            logger.debug(String.format("Forwarding: %s %s %s -> no mapping found", httpMethod, serverName, uri));
        }
        addHeadersForward(httpServletRequest,headers);
        byte[] body = requestExtractor.extraBody(httpServletRequest);
        RequestData data = new RequestData(httpMethod,uri,serverName,headers,body,httpServletRequest);
        preForwardRequestInterceptor.intercepter(data,mappingPropertise);
        if(data.isNeedReirect() && !isBlank(data.getRedirectUrl())){
            logger.debug(String.format("Redirecting to -> %s", data.getRedirectUrl()));
            httpServletResponse.sendRedirect(data.getRedirectUrl());
            return;
        }
        ResponseEntity<byte[]> responseEntity = requstForwarder.forwardDestination(data,traceId,mappingPropertise);
        processResponse(httpServletResponse,responseEntity);

    }

    private void processResponse(HttpServletResponse httpServletResponse,ResponseEntity<byte[]> responseEntity){
        httpServletResponse.setStatus(responseEntity.getStatusCodeValue());
        responseEntity.getHeaders().forEach((name,values) -> {
           values.forEach((value) ->  httpServletResponse.addHeader(name,value));
        });
        if(responseEntity.getBody() != null){
            try {
                httpServletResponse.getWriter().println(responseEntity.getBody());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addHeadersForward(HttpServletRequest request,HttpHeaders httpHeaders){
        List<String> forwarderFor = httpHeaders.get(X_FORWARDED_FOR_HEADER);
        if(forwarderFor == null){
            forwarderFor = new ArrayList<>(1);
        }
        forwarderFor.add(request.getRemoteAddr());
        httpHeaders.put(X_FORWARDED_FOR_HEADER,forwarderFor);
        httpHeaders.set(X_FORWARDER_PROTO_HEADER,request.getScheme());
        httpHeaders.set(X_FORWARDED_HOST_HEADER,request.getServerName());
        httpHeaders.set(X_FORWARDER_PORT_HEADER,String.valueOf(request.getServerPort()));
    }
}
