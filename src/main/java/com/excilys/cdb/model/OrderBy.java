package com.excilys.cdb.model;

public enum OrderBy {
	ID("computer.id"), NAME("computer.name"), INTRODUCED("introduced"), DISCONTINUED("discontinued"), COMPANY("company.name");
	
	private final String column;
	
	private OrderBy(String columnName) {
		this.column = columnName;
	}
	
	public String getColumn() {
		return column;
	}
}
