package com.kyung.springbootdata;

import com.kyung.springbootdata.account.Account;
import com.kyung.springbootdata.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class SpringbootdataApplication {

    @Autowired
    MongoTemplate mongoTemplate;

    // Repository 로 만들어 사용 가능
    @Autowired
    AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootdataApplication.class, args);
    }

    // 아래처럼 Bean 으로 등록할 수도 있다.(물론 Component 로 등록하는 방식도 가능... 이것도 가능할 뿐임)
    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            Account account = new Account();
            account.setUsername("aaa");
            account.setEmail("aaa@bbb.com");

//            mongoTemplate.insert(account);
            accountRepository.insert(account);

            System.out.println("finished");
        };
    }
}
