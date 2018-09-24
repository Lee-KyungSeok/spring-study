package com.ho.lec24.config;

import java.beans.PropertyVetoException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

// 여기서 DB 에 관한 설정을 진행한 후 주입해도 괜찮다.
@Configuration
public class DBConfig {

	@Bean
	public ComboPooledDataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
		dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUser("scott");
		dataSource.setPassword("tiger");
		dataSource.setMaxPoolSize(200);
		dataSource.setCheckoutTimeout(60000);
		dataSource.setMaxIdleTime(1800);
		dataSource.setIdleConnectionTestPeriod(600);
		
		return dataSource;

	}
	
}