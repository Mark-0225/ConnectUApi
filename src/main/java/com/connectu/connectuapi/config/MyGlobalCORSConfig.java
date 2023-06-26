package com.connectu.connectuapi.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyGlobalCORSConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允许跨域访问的路径
                        .allowedOrigins("http://localhost:8080","https://apifox.com/apidoc/shared-c32e3a6f-756f-43e3-aed6-5408aa8d0247/api-88082215","http://localhost:3000","http://10.0.104.210:3000") // 允许跨域访问的源
//                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE") // 允许请求的方法
                        .maxAge(168000) // 预检间隔时间
                        .allowedHeaders("*") // 允许头部设置
                        .allowCredentials(true); // 是否发送cookie
            }
        };
    }

    @Bean
    public FilterRegistrationBean<CoopFilter> coopFilter1() {
        FilterRegistrationBean<CoopFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CoopFilter());
        registrationBean.addUrlPatterns("/users/google");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CoepFilter> coepFilter1() {
        FilterRegistrationBean<CoepFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CoepFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}