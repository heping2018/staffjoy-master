package com.example.commonlib.commonlib.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(HttpServletResponse.SC_OK, "Operation is Successful"),
    ;

    final int code;

    final String msg;
}
