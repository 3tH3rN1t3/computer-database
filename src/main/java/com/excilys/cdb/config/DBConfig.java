package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DBConfig {
	@Bean
	public DataSource HikariDataSource() {
		return new HikariDataSource(new HikariConfig("/database.properties"));
	}
}