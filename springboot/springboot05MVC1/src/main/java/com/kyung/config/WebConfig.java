package com.kyung.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* WebMVC 에서 추가적인 설정을 하고 싶다면 WebMvcConfigurer 를 상속해서 Config 파일을 만들어야 한다.
    - 단 @EnableWebMvc 어노테이션은 쓰지 말아야 한다. => 이를 사용 시 Springboot 에서 제공하는 MVC 기능이 모두 사라지게 되고 커스터마이징 되기 때문

 */

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("m/**")
                .addResourceLocations("classpath:/m/")  // 반드시 슬래시(/) 로 끝나도록 설정할 것
                .setCachePeriod(20); // 커스텀 할때는 캐시컨드롤이 필요하다.
    }
}
