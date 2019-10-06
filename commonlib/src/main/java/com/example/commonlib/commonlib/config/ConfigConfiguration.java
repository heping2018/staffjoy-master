package com.example.commonlib.commonlib.config;

import com.example.commonlib.commonlib.envconfig.EnvConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigConfiguration {

    @Value("${spring.profiles.active:NA}")
    private String active;

    @Bean
    public EnvConfig createEnvConfig(){
        return EnvConfig.getLocalEnv(active);
    }
}
