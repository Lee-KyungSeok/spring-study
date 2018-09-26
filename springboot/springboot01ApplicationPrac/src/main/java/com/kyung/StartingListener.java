package com.kyung;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class StartingListener implements ApplicationListener<ApplicationStartingEvent> {

    // Application 이 시작할 때 실행된다.
    // 그런데 Application 이 등록이 되기 전에 실행되므로 이런 경우에는 main 에서 따로 등록 해주어야 한다.
    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("==============================");
        System.out.println("Application is starting");
        System.out.println("==============================");
    }
}
