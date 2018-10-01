package com.kyung.springjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "accountAuditAware") // auditing 기능을 사용하려면 이를 추가해야 한다.
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
