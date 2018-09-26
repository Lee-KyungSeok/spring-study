package com.kyung.springbootdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class PostgreSQLRunner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try(Connection connection = dataSource.getConnection()) { // try, catch 를 사용해서 항상 예외처리를 해주어야 한다.
            dataSource.getClass();
            connection.getMetaData().getURL();
            connection.getMetaData().getUserName();

            // query 를 실행시킬 수 있다.
            Statement statement = connection.createStatement();
            // postreSQL 에서는 USER 는 default name 이므로 다른 것을 사용하도록 한다.
            String sql = "CREATE TABLE USERS(id INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
            statement.executeUpdate(sql);
        }

        // jdbcTemplate 을 사용하면 예외처리 등을 자동으로 해준다,
        // - 오히려 더 가독성이 높은 에러를 보여준다.
        jdbcTemplate.execute("INSERT INTO account VALUES (1, 'kyungseok')");
    }
}