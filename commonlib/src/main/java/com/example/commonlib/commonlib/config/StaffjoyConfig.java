package com.example.commonlib.commonlib.config;


import com.example.commonlib.commonlib.envconfig.EnvConfig;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StaffjoyProps.class)
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
    public SentryClient getSentryClient(){
        SentryClient sentryClient = Sentry.init(staffjoyProps.getSentryDsn());
        sentryClient.setEnvironment(active);
        sentryClient.setRelease(staffjoyProps.getDeployEnv());
        sentryClient.addTag("service",name);
        return sentryClient;
    }
}
