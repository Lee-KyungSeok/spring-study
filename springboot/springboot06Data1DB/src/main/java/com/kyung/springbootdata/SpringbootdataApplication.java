package com.kyung.springbootdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootdataApplication.class, args);
    }
}

/* spring-jdbc 가 클래스패스에 있으면 자동 설정이 필요한 빈을 설정해준다. (즉, Autowired 로 주입받을 수 있다.)
    - DataSource
    - JdbcTemplate
 */