package com.kyung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringInitApplication {

    @ConfigurationProperties("server")
    @Bean
    public ServerProperties serverProperties() {
        return new ServerProperties();
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringInitApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}

/* 외부 설정 방법

   1. properties (우선순위는 15위 이다.)
    - application.properties 에 설정한 후
    - @Value("${kyung.name}") 와 같이 value 를 설정해 줄 수 있다.
    - 이 때 타입-세이프 프로퍼티 @ConfigurationProperties 를 만들고 설정할 수 있다.
    - 만약 third-party 라이브러리의 프로퍼티를 가져와야 하는 경우 @Bean 을 이용해서 가져와야 한다. (@Component 는 불가능하므로)

   참고> module 에서 test 의 resources 는 테스트 resources 로 바꿔줘야 한다.
 */
