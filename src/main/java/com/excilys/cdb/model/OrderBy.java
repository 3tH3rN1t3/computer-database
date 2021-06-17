package com.excilys.cdb.model;

public enum OrderBy {
	ID("computer.id"), NAME("computer.name"), INTRODUCED("computer.introduced"), DISCONTINUED("computer.discontinued"), COMPANY("company.name");
	
	private final String column;
	
	private OrderBy(String s) {
		this.column = s;
	}
	
	public String getColumn() {
		return column;
	}
}
