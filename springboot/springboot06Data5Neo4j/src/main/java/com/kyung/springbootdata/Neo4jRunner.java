package com.kyung.springbootdata;

import com.kyung.springbootdata.account.Account;
import com.kyung.springbootdata.account.AccountRepository;
import com.kyung.springbootdata.account.Role;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Neo4jRunner implements ApplicationRunner {

    // SessionFactory 를 이용할 수 있다.
//    @Autowired
//    SessionFactory sessionFactory;

    // Repository 이용하기
    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setEmail("aaa@bbb");
        account.setUsername("aa");

        Role role = new Role();
        role.setName("admin");

        account.getRoles().add(role);

//        Session session = sessionFactory.openSession();
//        session.save(account);
////        session.clear(); // session 을 비울 수 있다.
//        sessionFactory.close();

        accountRepository.save(account);

        System.out.println("finished");
    }
}
