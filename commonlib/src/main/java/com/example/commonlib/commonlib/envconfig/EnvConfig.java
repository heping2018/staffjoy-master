package com.example.commonlib.commonlib.envconfig;


import java.util.HashMap;
import java.util.Map;

/**
 * 配置类
 * 根据程序传入的参数确定使用哪个环境
 * 策略模式
 */
public class EnvConfig {


    private String name;
    private boolean debug;
    private String ex


    private static Map<String,EnvConfig> map = new HashMap<String,EnvConfig>();
    static {

    }
}
