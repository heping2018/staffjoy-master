package com.example.staffjoy.faraday.config;

import com.example.commonlib.commonlib.envconfig.EnvConfig;
import com.example.staffjoy.faraday.balancer.BalancerDestantions;
import com.example.staffjoy.faraday.filter.*;
import com.example.staffjoy.faraday.trace.LoggingTraceInterceptor;
import com.example.staffjoy.faraday.trace.ProxyTraceIntercepter;
import com.example.staffjoy.faraday.view.AsessLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@EnableConfigurationProperties(FaradayPropertise.class)
public class FaradayConfiguration {





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



}
