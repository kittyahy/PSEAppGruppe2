package com.pseandroid2.dailydataserver;

import com.pseandroid2.dailydataserver.onlineproject.AccessToProjectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class GeneralConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AccessToProjectInterceptor accessToProjectInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/greet");
        registry.addInterceptor(accessToProjectInterceptor).addPathPatterns("/OnlineDatabase/*").excludePathPatterns("/OnlineDatabase/Delta/time");
    }
}
