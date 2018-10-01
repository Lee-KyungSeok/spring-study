package com.kyung.springjpa.post;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostRepositoryTestConfig {

    @Bean
    public PostListener postListener() {
        return new PostListener();
    }

    // 리스너를 구현하고 싶지 않다면 아래처럼 직접 빈으로 등록할 때 구현해도 된다.
    /*
    @Bean
    public ApplicationListener<PostPublishedEvent> postListener2() {
        return postPublishedEvent -> {
            System.out.println("======================================");
            System.out.println(postPublishedEvent.getPost().getTitle() + " is published");
            System.out.println("======================================");
        };
    }
    */
}
