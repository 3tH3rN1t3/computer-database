package com.excilys.cdb.model;

public enum SearchBy {
	NAME("computer.name"), INTRODUCED("introduced"), DISCONTINUED("discontinued"), COMPANY("company.name");
	
	private final String column;
	
	private SearchBy(String s) {
		this.column = s;
	}
	
	public String getColumn() {
		return column;
	}
}
