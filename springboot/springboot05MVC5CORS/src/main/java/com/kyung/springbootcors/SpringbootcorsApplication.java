package com.kyung.springbootcors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
//    @CrossOrigin(origins = "https://localhost:18080") // cors 를 컨트롤러에도 추가시킬 수 있다.
public class SpringbootcorsApplication {

//    @CrossOrigin(origins = "https://localhost:18080") // cors 를 추가시킬 수 있다.
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootcorsApplication.class, args);
    }
}
