package com.example.staffjoy.faraday.view;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorPage {

    private String title;
    //错误的原因
    private String explanation;
    //http code
    private int headerCode;
    //错误页面地址
    private String linkText;
    //错误链接
    private String linkHref;
    //sentry ID
    private String sentryErrorId;
    //应用名称
    private String sentryPubicSn;
    private String imageBase64;
}
