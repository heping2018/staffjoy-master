package com.example.commonlib.commonlib.service;


import java.util.HashMap;
import java.util.Map;

/**
 * service服务管理类
 */
public class ServiceDirectory {
    private static Map<String,Service> map = new HashMap<>();

    static {
        /**
         * 账户服务
         */
        Service service = Service.builder()
                .backendDomain("account-service")
                .noCacheHtml(false)
                .restrictDev(true)
                .security(SecurityConstant.SEC_AUTHENTICATION)
                .build();
        map.put("account",service);
        service = Service.builder()
                .backendDomain(("app-service"))
                .noCacheHtml(true)
                .restrictDev(false)
                .security(SecurityConstant.SEC_AUTHENTICATION)
                .build();
        map.put("app",service);
        service = Service.builder()
                .backendDomain("company-service")
                .restrictDev(false)
                .noCacheHtml(true)
                .security(SecurityConstant.SEC_AUTHENTICATION)
                .build();
        map.put("company",service);
        service = Service.builder()
                .backendDomain("httpbin.org")
                .noCacheHtml(false)
                .restrictDev(true)
                .security(SecurityConstant.SEC_PUBLIC)
                .build();
        map.put("faraday",service);
        service = Service.builder()
                .backendDomain("ical-service")
                .security(SecurityConstant.SEC_PUBLIC)
                .noCacheHtml(false)
                .restrictDev(true)
                .build();
        map.put("ical",service);
        service = Service.builder()
                .backendDomain("myaccount-service")
                .security(SecurityConstant.SEC_AUTHENTICATION)
                .noCacheHtml(false)
                .restrictDev(true)
                .build();
        map.put("myaccount",service);
        service = Service.builder()
                .backendDomain("superpower-service")
                .security(SecurityConstant.SEC_AUTHENTICATION)
                .noCacheHtml(true)
                .restrictDev(true)
                .build();
        map.put("superpower",service);
        service = Service.builder()
                .backendDomain("whoami-service")
                .security(SecurityConstant.SEC_AUTHENTICATION)
                .noCacheHtml(false)
                .restrictDev(true)
                .build();
        map.put("whoami",service);
        service = Service.builder()
                .backendDomain("www-service")
                .security(SecurityConstant.SEC_PUBLIC)
                .noCacheHtml(false)
                .restrictDev(true)
                .build();
        map.put("www",service);
    }

    public static Map<String,Service> getMap(){
        return map;
    }

}
