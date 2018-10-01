package com.kyung.springjpa.post;

import org.springframework.context.ApplicationEvent;

// Event 를 생성
public class PostPublishedEvent extends ApplicationEvent {

    private final Post post;

    public PostPublishedEvent(Object source) {
        super(source);
        this.post = (Post) source;
    }

    public Post getPost() {
        return post;
    }
}
