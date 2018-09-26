package com.kyung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringInitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringInitApplication.class, args);
    }
}

/* SpringWebMVC 정적 리소스 지원
   위치
    - classpath:/static
    - classpath:/public
    - classpath:/resources/
    - classpath:/META-INF/resources
        - 예) “/hello.html” => /static/hello.html
    - spring.mvc.static-path-pattern: 맵핑 설정 변경 가능 (application.properties)
    - spring.mvc.static-locations: 리소스 찾을 위치 변경 가능 (application.properties)

   ResourceHttpRequestHandler 가 처리
    - WebMvcConfigurer의 addRersourceHandlers로 커스터마이징 가능하다. (web 의 Path 와 내부 Resource Path 등 설정 가능)
 */