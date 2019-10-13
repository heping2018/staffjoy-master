package com.example.commonlib.commonlib.config;


import com.example.commonlib.commonlib.aop.SentryClientAspect;
import com.example.commonlib.commonlib.envconfig.EnvConfig;
import com.github.structlog4j.StructLog4J;
import com.github.structlog4j.json.JsonFormatter;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@EnableConfigurationProperties(StaffjoyProps.class)
@Import(SentryClientAspect.class)
public class StaffjoyConfig {
    @Value("${spring.profiles.active}")
    private String active;
    @Value("${spring.application.name}")
    private String name;

    @Autowired
    private StaffjoyProps staffjoyProps;

    @Bean
    public EnvConfig getEnvConfig(){
        return EnvConfig.getLocalEnv(active);
    }
    @Bean
    //sentry
    public SentryClient getSentryClient(){
        SentryClient sentryClient = Sentry.init(staffjoyProps.getSentryDsn());
        sentryClient.setEnvironment(active);
        sentryClient.setRelease(staffjoyProps.getDeployEnv());
        sentryClient.addTag("service",name);
        return sentryClient;
    }
    //定义sentry输出格式
    @PostConstruct
    public void init(){
        StructLog4J.setFormatter(JsonFormatter.getInstance());
        StructLog4J.setMandatoryContextSupplier(() -> new Object[]{
                "env", active,
                "service", name});
    }

    @PreDestroy
    public void destroy(){
        getSentryClient().closeConnection();
    }
}
