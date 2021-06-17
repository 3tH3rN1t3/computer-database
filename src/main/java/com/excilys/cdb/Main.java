package com.excilys.cdb;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.spring.config.DBConfig;
import com.excilys.cdb.spring.config.PersistenceJPAConfig;
import com.excilys.cdb.spring.config.SpringConfig;
import com.excilys.cdb.ui.CLI;

public class Main {

	private static ApplicationContext applicationContext;
	
	public static void main(String... args) throws SQLException {
		applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class, PersistenceJPAConfig.class);
		CLI cli = (CLI) applicationContext.getBean("cli");
		cli.runCLI();
	}
	
}
