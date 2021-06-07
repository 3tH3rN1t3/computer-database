package com.excilys.cdb;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.config.TestConfig;
import com.excilys.cdb.ui.CLI;

public class Main {

	private static ApplicationContext applicationContext;

	public static void main(String... args) throws SQLException {
		//applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
		applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
		CLI cli = (CLI) applicationContext.getBean("cli");
		cli.runCLI();
	}
	
}
