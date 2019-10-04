package com.example.commonlib.commonlib.envconfig;


import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置类
 * 根据程序传入的参数确定使用哪个环境
 * 策略模式
 */

@Data
@Builder
public class EnvConfig {


    private String name;
    private boolean debug;
    private String externalApex;
    private String internalApex;
    private String schame;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static Map<String,EnvConfig> map = new HashMap<String,EnvConfig>();
    static {
        /*
        开发环境
         */
        EnvConfig envConfig = EnvConfig.builder().name(EnvConstant.ENV_DEV)
                .debug(true)
                .externalApex("staffjoy.local")
                .internalApex(EnvConstant.ENV_DEV)
                .schame("http")
                .build();
        map.put(EnvConstant.ENV_DEV,envConfig);
        /*
        测试环境
         */
        envConfig = EnvConfig.builder().name(EnvConstant.ENV_TEST)
                .debug(true)
                .externalApex("staffjoy.local")
                .internalApex(EnvConstant.ENV_TEST)
                .schame("http")
                .build();
        map.put(EnvConstant.ENV_TEST,envConfig);

        /*
        准生产环境
         */
        envConfig = EnvConfig.builder().name(EnvConstant.ENV_UAT)
                .debug(true)
                .externalApex("staffjoy.local")
                .internalApex(EnvConstant.ENV_UAT)
                .schame("https")
                .build();
        map.put(EnvConstant.ENV_UAT,envConfig);

        /*
        生产环境
         */
        envConfig = EnvConfig.builder().name(EnvConstant.ENV_PROD)
                .debug(true)
                .externalApex("staffjoy.local")
                .internalApex(EnvConstant.ENV_PROD)
                .schame("https")
                .build();
        map.put(EnvConstant.ENV_PROD,envConfig);

    }

    public static EnvConfig getLocalEnv(String env){
        EnvConfig envConfig = map.get(env);
        if(envConfig == null){
            env = EnvConstant.ENV_DEV;
            envConfig = map.get(env);
        }
        return envConfig;
    }
}
