package com.kyung.springbootsecurity;

import com.kyung.springbootsecurity.account.Account;
import com.kyung.springbootsecurity.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account kyungseok = accountService.createAccount("kyungseok", "1234");
        System.out.println(kyungseok.getUsername() + " / password: " + kyungseok.getPassword()); // password 는 로그로 찍지 말것 ㅎㅎㅎㅎㅎ
    }
}
