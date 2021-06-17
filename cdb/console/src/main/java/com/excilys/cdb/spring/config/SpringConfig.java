package com.excilys.cdb.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.excilys.cdb.persistence"
		, "com.excilys.cdb.controller"
		, "com.excilys.cdb.service"
		, "com.excilys.cdb.ui"
		, "com.excilys.cdb.spring.aspect"
})
public class SpringConfig {
}