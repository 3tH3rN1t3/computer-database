package com.excilys.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = { "com.excilys.cdb.persistence"
		, "com.excilys.cdb.mapper"
		, "com.excilys.cdb.validator"
		, "com.excilys.cdb.controller"
		, "com.excilys.cdb.service"
		, "com.excilys.cdb.ui"
})
public class SpringConfig {
}