package com.excilys.cdb;

import java.sql.SQLException;

import com.excilys.cdb.ui.CLI;

public class Main {

	private Main() { };

	public static void main(String[] args) throws SQLException {
		CLI cli = new CLI();
		cli.runCLI();
	}
	
}
