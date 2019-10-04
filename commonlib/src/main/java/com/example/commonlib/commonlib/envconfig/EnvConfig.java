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
        EnvConfig envConfig = EnvConfig.builder().name(EnvConstant.ENV_DEV)
                .debug(true)
                .externalApex("staffjoy.local")
                .internalApex(EnvConstant.ENV_DEV)
                .schame("http")
                .build();
        map.put(EnvConstant.ENV_DEV,envConfig);
    }
}
