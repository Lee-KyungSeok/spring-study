package com.kyung.springjpa.post;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

// 이는 bean 으로 등록해야 한다. (지금은 테스트 config 에 등록했다.
//public class PostListener implements ApplicationListener<PostPublishedEvent> {
public class PostListener {

    // 이벤트가 일어났을 때 알려줄 수 있다.
//    @Override
    @EventListener // 만약 ApplicationListener 를 등록하지 않는다면 이 어노테이션을 달아주면 된다.
    public void onApplicationEvent(PostPublishedEvent postPublishedEvent) {
        System.out.println("======================================");
        System.out.println(postPublishedEvent.getPost().getTitle() + " is published");
        System.out.println("======================================");
    }
}
