package com.example.staffjoy.faraday.config;

import com.example.commonlib.commonlib.config.StaffjoyConfig;
import com.example.commonlib.commonlib.envconfig.EnvConfig;
import com.example.staffjoy.faraday.balancer.Balancer;
import com.example.staffjoy.faraday.balancer.BalancerDestantions;
import com.example.staffjoy.faraday.filter.*;
import com.example.staffjoy.faraday.http.HttpProvide;
import com.example.staffjoy.faraday.http.RequestExtractor;
import com.example.staffjoy.faraday.http.RequstForwarder;
import com.example.staffjoy.faraday.intercepter.AuthRequestInterceptor;
import com.example.staffjoy.faraday.intercepter.PreForwardRequestInterceptor;
import com.example.staffjoy.faraday.mapping.ConfigrutionMappingProvider;
import com.example.staffjoy.faraday.mapping.MappingProvider;
import com.example.staffjoy.faraday.mapping.MappingValidator;
import com.example.staffjoy.faraday.mapping.ProgrammaticMappingsProvider;
import com.example.staffjoy.faraday.trace.LoggingTraceInterceptor;
import com.example.staffjoy.faraday.trace.ProxyTraceIntercepter;
import com.example.staffjoy.faraday.view.AsessLoader;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.util.Optional;

@Configuration
@EnableConfigurationProperties({FaradayPropertise.class,StaffjoyPropertise.class})
@Import(value= StaffjoyConfig.class)
public class FaradayConfiguration {




    public ReverProxyFilter fradayRever(RequestExtractor requestExtractor, ProxyTraceIntercepter traceInterceptor,
                                        MappingProvider mappingProvider, PreForwardRequestInterceptor preForwardRequestInterceptor,
                                        RequstForwarder requstForwarder){

        return new ReverProxyFilter(requestExtractor,traceInterceptor,mappingProvider,preForwardRequestInterceptor,requstForwarder);
    }


    @Bean
    @ConditionalOnMissingBean
    public RequestExtractor createRequestExtractor(){
        return new RequestExtractor();
    }

    @Bean
    @ConditionalOnMissingBean
    public PreForwardRequestInterceptor createPreForwardRequestInterceptor(StaffjoyPropertise staffjoyPropertise,EnvConfig envConfig){
        String sginingScoDecret = staffjoyPropertise.getSginingScoDecret();
        return new AuthRequestInterceptor( sginingScoDecret, envConfig);
    }
    @Bean
    @ConditionalOnMissingBean
    public RequstForwarder createRequestForwarder(FaradayPropertise faradayPropertise, Optional<MeterRegistry> meterRegistry
            , Balancer balancer, HttpProvide httpProvide
            , ProxyTraceIntercepter proxyTraceIntercepter){
        return new RequstForwarder(faradayPropertise,meterRegistry,balancer,httpProvide,proxyTraceIntercepter);
    }

    @Bean
    @ConditionalOnMissingBean
    public MappingProvider createMappingProvider(FaradayPropertise faradayPropertise,
                                                 MappingValidator mappingValidator,HttpProvide httpProvide,EnvConfig envConfig){
        if(faradayPropertise.isEnableProgrammaticMapping()){
            return new ProgrammaticMappingsProvider(faradayPropertise,mappingValidator,
                    httpProvide,envConfig);
        }else {
            return new ConfigrutionMappingProvider(faradayPropertise,mappingValidator,
                    httpProvide);
        }
    }

    /**
     * 转发核心过滤
     */
    public FilterRegistrationBean<ReverProxyFilter> reverProxyFilterFilterRegistrationBean(ReverProxyFilter reverProxyFilter){
        FilterRegistrationBean<ReverProxyFilter> reverProxyFilterFilterRegistrationBean =
                new FilterRegistrationBean<>(reverProxyFilter);
        //指定filter顺序
        reverProxyFilterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 100);
        return reverProxyFilterFilterRegistrationBean;
    }

    /**
     * 注册健康检查过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean<HealthCheckFliter> healthCheckFliterFilterRegistrationBean(){
        FilterRegistrationBean<HealthCheckFliter> healthCheckFliterFilterRegistrationBean =
                new FilterRegistrationBean<>(new HealthCheckFliter());
        //指定filter顺序
        healthCheckFliterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 70);
        return healthCheckFliterFilterRegistrationBean;
    }
    /**
     * 注册favincon 过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean<FavinconFliter> favinconFliterFilterRegistrationBean(){
        FilterRegistrationBean<FavinconFliter> favinconFliterFilterRegistrationBean = new
                FilterRegistrationBean<>(new FavinconFliter(AsessLoader.favinconBytes));
        favinconFliterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 78);
        return favinconFliterFilterRegistrationBean;
    }
    /**
     * 注册http过滤器
     */
    @Bean
    public FilterRegistrationBean<SecurityFilter> securityFilterFilterRegistrationBean(EnvConfig envConfig){
        FilterRegistrationBean<SecurityFilter> securityFilterFilterRegistrationBean = new
                FilterRegistrationBean<>(new SecurityFilter(envConfig));
        securityFilterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 80);
        return securityFilterFilterRegistrationBean;
    }

    /**
     * 注册默认登录
     */
    @Bean
    public FilterRegistrationBean<NakeDomianFilter> nakeDomianFilterFilterRegistrationBean(EnvConfig envConfig){
        FilterRegistrationBean<NakeDomianFilter> nakeDomianFilterFilterRegistrationBean = new
                FilterRegistrationBean<>(new NakeDomianFilter(envConfig));
        nakeDomianFilterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 90);
        return nakeDomianFilterFilterRegistrationBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public BalancerDestantions createBalancer(){
        return new BalancerDestantions();
    }

    @Bean
    @ConditionalOnMissingBean
    public FaradayPropertise createFaradayPropertise(){
        return new FaradayPropertise();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoggingTraceInterceptor createLoggingTrace(){
        return new LoggingTraceInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProxyTraceIntercepter createProxyTrace(LoggingTraceInterceptor loggingTraceInterceptor,FaradayPropertise faradayPropertise){
        return new ProxyTraceIntercepter(loggingTraceInterceptor,faradayPropertise);
    }

    @Bean
    @ConditionalOnMissingBean
    public MappingValidator faradayMappingsValidator() {
        return new MappingValidator();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpProvide faradayHttpProvice(){
        return new HttpProvide();
    }
}
