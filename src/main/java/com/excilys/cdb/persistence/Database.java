package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Component
@Scope("singleton")
public class Database {
	private static Logger logger = LogManager.getLogger(Database.class);
	
	private static final String DB_PROPERTIES_FILE_PATH = "/database.properties";
	
	private static HikariConfig config;
	
	private DataSource ds;
	
	public Database() {
		config = new HikariConfig(DB_PROPERTIES_FILE_PATH);
		ds = new HikariDataSource(config);
		logger.info("Connection successfull");
	}
	
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
