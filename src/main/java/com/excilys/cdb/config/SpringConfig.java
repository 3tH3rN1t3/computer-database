package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "com.excilys.cdb.persistence"
		, "com.excilys.cdb.controller"
		, "com.excilys.cdb.service"
		, "com.excilys.cdb.ui"
})
public class SpringConfig {
	@Bean
	public DataSource HikariDataSource() {
		return new HikariDataSource(new HikariConfig("/database.properties"));
	}
}