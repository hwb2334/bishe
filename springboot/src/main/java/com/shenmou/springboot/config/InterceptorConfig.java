package com.shenmou.springboot.config;

import com.shenmou.springboot.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/register",
                        "/createImageCode", "/verifyImageCode");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
