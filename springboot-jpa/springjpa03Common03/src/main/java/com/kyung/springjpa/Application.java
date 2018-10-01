package com.kyung.springjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(repositoryImplementationPostfix = "Default") // 만약 Impl 을 붙이고 싶지 않다면 이를 설정해 주어야 한다. 하지만 Impl 로 하는게 날듯??
@EnableJpaRepositories(repositoryBaseClass = SimpleMyRepository.class) // 기본 리포지토리를 커스텀했으면 baseclass 에 지정해주어야 한다.
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
