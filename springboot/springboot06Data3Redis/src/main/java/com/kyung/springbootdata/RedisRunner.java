package com.kyung.springbootdata;

import com.kyung.springbootdata.account.Account;
import com.kyung.springbootdata.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisRunner implements ApplicationRunner {

    // RedisTemplate 을 통해서 사용할 수 있다.
    @Autowired
    StringRedisTemplate redisTemplate;

    // Repository 를 상속받아 사용할 수 있다.
    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Value 를 세팅할 수 있다.
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set("kyungseok", "cory");
        values.set("springboot", "2.0");
        values.set("hello", "world");

        Account account = new Account();
        account.setEmail("kyungseok@email.com");
        account.setUsername("kyungseok");

        accountRepository.save(account);

        Optional<Account> byId = accountRepository.findById(account.getId());
        System.out.println(byId.get().getUsername());
        System.out.println(byId.get().getEmail());
    }
}
