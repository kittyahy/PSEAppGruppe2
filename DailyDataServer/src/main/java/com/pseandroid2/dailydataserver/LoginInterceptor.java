package com.pseandroid2.dailydataserver;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * only skalaton
 * #TODO javadoc,Test, implemetierung
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Firebase auth
        String name = "TODO"; // firebasetoken.getUid();
        request.setAttribute("name",name);
        return HandlerInterceptor.super.preHandle(request, response, handler);

    }
}
