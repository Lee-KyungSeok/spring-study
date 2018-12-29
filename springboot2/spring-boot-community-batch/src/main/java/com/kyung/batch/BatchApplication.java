package com.kyung.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing // 스프링 부트 배치 스타터에 미리 정의된 설정들을 실행시킨다. (배치에 필요한 JobBuilder, StepBuilder, 등 다양한 설정이 자동으로 주입된다.)
public class BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}
