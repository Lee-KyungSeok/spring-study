package com.kyung.springbootsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // view 만 리턴하는 경우는 아래와 같이 viewController 로만으로도 할 수 있다.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello/view").setViewName("hello");
    }
}
