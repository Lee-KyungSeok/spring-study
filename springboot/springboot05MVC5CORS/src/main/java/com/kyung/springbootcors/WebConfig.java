package com.kyung.springbootcors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Configure 에서 각각의 api 를 등록할 수 있다.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/hello") // /** 로 설정하면 전부 다 설정하겠다는 뜻
                .allowedOrigins("http://localhost:18080");
    }
}
