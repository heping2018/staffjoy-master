package com.example.commonlib.commonlib.service;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class Service {
    //权限public，admin，authenticated
    private int security;
    //是否是支持生产环境
    private boolean restrictDev;
    //service名称
    private String backendDomain;
    //是否缓存页面
    private boolean noCacheHtml;
}
