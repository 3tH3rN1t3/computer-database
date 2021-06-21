package com.excilys.cdb.ui;

import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.controller.CLIController;

@Component("cli")
@Scope("singleton")
public class CLI {
	
	private CLIController controller;
	
	public CLI(CLIController controller) {
		this.controller = controller;
	}
	
	public void runCLI() {
		int choice = 0;
		do {
			try {
				controller.getView().printMenu();
				choice = controller.getAsker().askChoice();
				controller.executeChoice(choice);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} while (choice != MenuOption.EXIT.getNumber());
	}
}