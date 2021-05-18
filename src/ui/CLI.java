package ui;

import java.sql.SQLException;

import controller.CLIController;

public class CLI {
	private CLIController ctrl;
	
	public CLI() {
		ctrl = new CLIController();
	}
	
	public void runCLI() throws SQLException {
		int choice;
		do {
			ctrl.getView().printMenu();
			choice = ctrl.getAsker().askChoice();
			ctrl.executeChoice(choice);
		} while (choice != MenuOption.EXIT.getNumber());
	}
}
