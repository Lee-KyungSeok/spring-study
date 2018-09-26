package com.kyung;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1) // Order 를 이용해서 실행 순서를 지정할 수 있다.(낮은게 먼저 실행)
public class SampleRunner implements ApplicationRunner {

    // 어플리케이션 실행한 뒤 실행하기
    // 만약 CommandLineRunner 를 사용하면 String... args 로 인자를 받기 때문에 조금 헤깔릴 수 있다.
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("foo: " + args.containsOption("foo"));
        System.out.println("bar: " + args.containsOption("bar"));

    }
}
