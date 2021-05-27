package com.excilys.cdb.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//singleton
public class Database {
	private static Logger logger = LogManager.getLogger(Database.class);
	
	private static final String DB_PROPERTIES_FILE_PATH = "/database.properties";
	private static final String DB_PROPERTY_DRIVER = "jdbc.driver";
	private static final String DB_PROPERTY_URL = "jdbc.url";
	private static final String DB_PROPERTY_LOGIN = "jdbc.username";
	private static final String DB_PROPERTY_PASSWORD = "jdbc.password";
	
	private static Database db = null;
	private DataSource ds = null;
	
	private Database() throws IOException {
		try {
			loadProperties();
			logger.info("Database successfully created");
		} catch (IOException e) {
			logger.fatal("Error during DataSource creation", e);
			throw new IOException("Une erreur fatale est survenue");
		}
	}
	
	public static Database getInstance() throws IOException {
		if(db == null) {
			db = new Database();
		}
		return db;
	}
	
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	private void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(Database.class.getResourceAsStream(DB_PROPERTIES_FILE_PATH));
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(properties.getProperty(DB_PROPERTY_DRIVER));
		ds.setUrl(properties.getProperty(DB_PROPERTY_URL));
		ds.setUsername(properties.getProperty(DB_PROPERTY_LOGIN));
		ds.setPassword(properties.getProperty(DB_PROPERTY_PASSWORD));
		logger.info(ds.getUrl());
		this.ds = ds;
	}
}
