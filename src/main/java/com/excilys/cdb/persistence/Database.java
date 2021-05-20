package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

//singleton
public class Database {
	private static Database db = null;
	private static final String URL = "jdbc:mysql://localhost/computer-database-db";
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	
	private Database() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static Database getDB() {
		if(db == null) {
			db = new Database();
		}
		return db;
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
