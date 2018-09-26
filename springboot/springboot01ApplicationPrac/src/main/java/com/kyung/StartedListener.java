package com.kyung;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

    // Application 이 시작된 이후 진행된다.(마지막에)
    // 이는 Application 이 만들어진 이후 실행되므로 Component 를 통해서 Bean 으로 등록한다.(addListener 필요 X)
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        System.out.println("==============================");
        System.out.println("Application is started");
        System.out.println("==============================");
    }
}
