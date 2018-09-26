package com.kyung.springbootdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/* Runner 와 datasource, jdbctemplate 은 모두 동일하게 사용 가능하다.

 */

@Component
public class H2Runner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try(Connection connection = dataSource.getConnection()) { // try, catch 를 사용해서 항상 예외처리를 해주어야 한다.
            // 기본적인 인메모리 DB 를 혹인할 수 있다.
            connection.getMetaData().getURL();      // "testdb" 로 연결됨
            connection.getMetaData().getUserName(); // "sa" 로 연결됨

            // query 를 실행시킬 수 있다.
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE USER(id INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
            statement.executeUpdate(sql);
        }

        // jdbcTemplate 을 사용하면 예외처리 등을 자동으로 해준다,
        // - 오히려 더 가독성이 높은 에러를 보여준다.
        jdbcTemplate.execute("INSERT INTO user VALUES (1, 'kyungseok')");
    }
}
/* H2 콘솔을 사용하고 싶다면
    1. spring-boot-devtools를 추가하거나... (spring.h2.console.enabled=true 만 추가.)
    2. /h2-console로 접속 (이 path도 바꿀 수 있음)
   로 사용 가능하다.
 */