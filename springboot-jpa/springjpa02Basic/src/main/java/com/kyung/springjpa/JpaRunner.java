package com.kyung.springjpa;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Transactional // 데이터는 transaction 으로 날라가므로 이를 설정해 주어야 한다.
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext // entityManager 를 주입받을 수 있다 => 핵심!!!
    EntityManager entityManager;


//    @Transactional // transacctional 은 transaction 이 날라가는 장소에 넣으면 된다.(혹은 전체에)
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("kyungseok");
        account.setPassword("jpa");
//        entityManager.persist(account);

        Study study = new Study();
        study.setName("Spring Data JPA");

        /*
        study.setOwner(account); // 양방향인 경우 여기서 설정해야 한다.(주의!!!)
        account.getStudies().add(study); // 양방향일 때는 이는 optional 이다.
        */
        // 아래처럼 메서드를 만들어서 설정해도 된다.
        account.addStudy(study);

        // session 으로 저장 가능
        Session session = entityManager.unwrap(Session.class);
        session.save(account);
        session.save(study);
    }
}
