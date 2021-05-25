package com.excilys.cdb.ui;

import java.io.IOException;
import java.sql.SQLException;

import com.excilys.cdb.controller.CLIController;

public class CLI {
	private CLIController ctrl;
	
	public CLI() {
		ctrl = new CLIController();
	}
	
	public void runCLI() {
		int choice = 0;
		try {
			do {
				try {
					ctrl.getView().printMenu();
					choice = ctrl.getAsker().askChoice();
					ctrl.executeChoice(choice);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			} while (choice != MenuOption.EXIT.getNumber());
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
