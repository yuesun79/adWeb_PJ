package com.fudan.se.community.interceptor;


import com.fudan.se.community.annotation.AdminLoginToken;
import com.fudan.se.community.annotation.PassToken;
import com.fudan.se.community.exception.TokenFail;
import com.fudan.se.community.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.method.HandlerMethod;



import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(handler.toString());
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        System.out.println(method);
        //跨域请求会首先发送一个options请求，直接返回正常状态并通过拦截器
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            log.info("走过了option请求");
            return true;
        }

        // 检查该方法上是否有 PassToken 的注解
        System.out.println(method.isAnnotationPresent(AdminLoginToken.class));
        if (method.isAnnotationPresent(AdminLoginToken.class)) {
            AdminLoginToken passToken = method.getAnnotation(AdminLoginToken.class);
            if (passToken.required()) {
                response.setCharacterEncoding("UTF-8");
                String token = request.getHeader("Token"); //Authorization
                boolean result = TokenUtil.verify(token);
                boolean isAdmin = TokenUtil.verifyAdmin(token);
                if (result&&isAdmin){
                    return true;
                }else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message", "You are not a teacher or ta,or token has failed!");
                    jsonObject.put("code", 402);
                    response.getWriter().append(jsonObject.toString());
                    response.setStatus(402);
                    response.setContentType("You are not a teacher or ta,or token has failed!");
                    return false;

                }

            }
        } else {
            response.setCharacterEncoding("UTF-8");
            String token = request.getHeader("Token"); //Authorization
            System.out.println(token);
            if (token != null) {
                System.out.println("TOKEN is"+token);
                boolean result = TokenUtil.verify(token);
                if (result) {
                    log.info("TOKEN 验证通过，TokenInterceptor拦截器放行");
                    return true;
                }
            }
        }
        // 否则不通过，验证失败
        response.setContentType("application/json;charset=utf8");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "token verify fail or token missing");
            jsonObject.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().append(jsonObject.toString());
            response.setStatus(401);
            response.setContentType("token verify fail");
            log.info("验证失败，未通过TokenInterceptor拦截器");
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return false;
    }
}
