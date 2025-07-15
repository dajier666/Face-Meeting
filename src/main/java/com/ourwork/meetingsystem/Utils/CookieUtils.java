package com.ourwork.meetingsystem.Utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtils {
    //从请求的cookie中获取用户的id
    public static Integer getUserMessage(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return JwtUtils.parseJWT(cookie.getValue()).get("id", Integer.class);
            }
        }
        return null;
    }
    //仅获取用户携带的令牌
    public static String getCookieToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
