package com.excilys.cdb.model;

public enum OrderBy {
	ID("computer.id"), NAME("computer.name"), INTRODUCED("introduced"), DISCONTINUED("discontinued"), COMPANY("company.name");
	
	private final String string;
	
	private OrderBy(String s) {
		this.string = s;
	}
	
	public String getString() {
		return string;
	}
}
