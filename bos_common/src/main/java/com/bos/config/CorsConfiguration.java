package com.bos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 跨域配置，解决全部接口的跨域设置
 */
@Configuration
public class CorsConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true) //响应头表示是否可以将对请求的响应暴露给页面。返回true则可以,其他值均不可以。
                .maxAge(3600);
        super.addCorsMappings(registry);
    }
}
