package com.kyung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException {
        // jdbc 정보
        String url = "jdbc:postgresql://localhost:5432/springdata";
        String user = "kyungseok";
        String password = "pass";

        // 커넥션 확인 (try-with 구문 사용)
        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection created: " +connection);

            // DDL
//            String sql = "CREATE TABLE account (id INT, username varchar(255), password varchar(255));";
            // DML
            String sql = "INSERT INTO account VALUES(1, 'kyungseok', 'pass')";

            // preparedStatement 를 이용해서 sql 문 실행
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.execute();
            }
        }
    }
}
