package com.excilys.cdb.ui;

public enum MenuOption {
	LIST_COMPUTERS(1), LIST_COMPANIES(2),
	SHOW_DETAILS(3),
	CREATE_COMPUTER(4), UPDATE_COMPUTER(5), DELETE_COMPUTER(6),
	EXIT(7);
	
	private int number;
	
	MenuOption(int number) {
		this.number = number;
	}
	
	public static Boolean isValid(int n) {
		return (n >= LIST_COMPUTERS.getNumber() && n <= EXIT.getNumber());
	}
	
	public int getNumber() {
		return number;
	}
}
