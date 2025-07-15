package com.ourwork.meetingsystem.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ourwork.meetingsystem.Entity.Result;
import com.ourwork.meetingsystem.Utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override//返回值代表是否放行,true放行,false不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url= request.getRequestURL().toString();
        log.info("请求的url：{}",url);
        if (url.contains("login")){
            return true;
        }
        String jwt=null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                }
            }
        }

        if(!StringUtils.hasLength(jwt)){
            log.info("令牌为空，返回未登录信息");
            Result error = Result.error("not login,please go to  localhost:8080/login    ");
            String s = JSONObject.toJSONString(error);
            response.getWriter().write(s);
            return false;
        }
        try{
            JwtUtils.parseJWT(jwt);
        }catch(Exception e){
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录信息");
            Result error = Result.error("not login,please go to  localhost:8080/login    ");
            String s = JSONObject.toJSONString(error);
            response.getWriter().write(s);
            return false;
        }
        //放行前逻辑
        log.info("令牌合法");
        //放行后逻辑
        return true;
    }
/*
    @Override//资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override//视图渲染完毕后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }*/
}
