package com.yyq.config;

import com.yyq.interceptor.CheckToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CheckToken checkToken;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkToken)
                .addPathPatterns("/queryByToken");
    }
}
