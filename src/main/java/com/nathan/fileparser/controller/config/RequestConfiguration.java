package com.nathan.fileparser.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class RequestConfiguration extends WebMvcConfigurerAdapter{

    @Autowired 
    HandlerInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      
      registry.addInterceptor(requestInterceptor).addPathPatterns("/**");;
    }

}
