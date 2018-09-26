package com.kyung;

import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// 웹서버가 초기화 되면 리스너가 호출이 된다.
@Component
public class PortListener implements ApplicationListener<ServletWebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent servletWebServerInitializedEvent) {
        // 아래처럼 포트를 가져올 수 있다.
        ServletWebServerApplicationContext applicationContexttext = servletWebServerInitializedEvent.getApplicationContext();
        applicationContexttext.getWebServer().getPort();
    }
}
