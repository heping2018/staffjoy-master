package com.example.commonlib.commonlib.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class Sessions {
    public static String getToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies== null || cookies.length == 0) return null;
        Cookie tokencookien = Arrays.stream(cookies)
                .filter(cookie -> AuthConstant.COOKIE_NAME.equals(cookie.getName()))
                .findAny().orElse(null);
        if(tokencookien == null) return null;
        return tokencookien.getValue();

    }
}
