package com.excilys.cdb.spring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.excilys.cdb.persistence"
		, "com.excilys.cdb.controller"
		, "com.excilys.cdb.service"
		, "com.excilys.cdb.ui"
		, "com.excilys.cdb.spring.aspect"
})
public class SpringConfig {
	@Bean
	public DataSource HikariDataSource() {
		return new HikariDataSource(new HikariConfig("/database.properties"));
	}
}