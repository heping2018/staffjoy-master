package com.example.staffjoy.faraday.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorPageFactory {
    @Autowired
    private AsessLoader asessLoader;

    public ErrorPage buildTimeOutErrorPage(){
        return ErrorPage.builder().title("Timeout Error")
                .explanation("Sorry,our servers seem to be slow. Please try again in moment")
                .headerCode(HttpStatus.GATEWAY_TIMEOUT.value())
                .linkText("Click here to check out our system status page")
                .linkHref("http://status.staffjoy.xyz")
                .imageBase64(asessLoader.imageBase64)
                .build();
    }

    public ErrorPage buildForbiddenErrorPage(){
        return ErrorPage.builder().title("Access Forbidden")
                .explanation("Sorry it looks like you do not have permission to access this page")
                .headerCode(HttpStatus.FORBIDDEN.value())
                .linkText("Contact our support team for help")
                .linkHref("mailto:help@staffjoy.xyz")
                .imageBase64(asessLoader.imageBase64)
                .build();
    }

    public ErrorPage buildInternalServerErrorPage(){
        return ErrorPage.builder().title("Internal Server Error")
                .explanation("Something broke .We are paging our engineers to look at it immedi")
                .headerCode(HttpStatus.EXPECTATION_FAILED.value())
                .linkText("Click here to check out our system status page")
                .linkHref("http://status.staffjoy.xyz")
                .imageBase64(asessLoader.imageBase64)
                .build();
    }
}
