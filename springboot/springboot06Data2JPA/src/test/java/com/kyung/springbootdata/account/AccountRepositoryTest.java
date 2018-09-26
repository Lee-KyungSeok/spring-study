package com.kyung.springbootdata.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest // test 인 경우 h2 를 사용하므로 이를 의존성에 추가하여 사용하도록 한다. (이렇게 slicing test 를 더 권장한다.)
//@SpringBootTest(properties = "spring.datasource.url=''") // postgreSQL 을 이용하고 싶다면 통합테스트로 실행해야 하면 property 를 바꿔 test용 db 로 접근하는 것이 좋다.
public class AccountRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void di() throws SQLException {
        // 아래를 확인하면 H2 를 이용함을 알 수 있다.
//        try(Connection connection = dataSource.getConnection()) {
//            DatabaseMetaData metaData = connection.getMetaData();
//            System.out.println(metaData.getURL());
//            System.out.println(metaData.getDriverName());
//            System.out.println(metaData.getUserName());
//        }

        Account account = new Account();
        account.setUsername("KyungSeok");
        account.setPassword("pass");

        Account newAccount = accountRepository.save(account);

        assertThat(newAccount).isNotNull();

        // 데이터가 잘 들어가 있는지 확인한다.
        Optional<Account> existingAccount = accountRepository.findByUsername(newAccount.getUsername());
        assertThat(existingAccount).isNotEmpty();
        // 들어있지 않는 데이터인지 확인한다.
        Optional<Account> nonExistingAccount = accountRepository.findByUsername("Lee");
        assertThat(nonExistingAccount).isEmpty();
    }

}