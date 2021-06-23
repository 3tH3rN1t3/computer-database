package com.excilys.cdb.model;

public enum Order {
	ASC, DESC;
	
	public static Order getEnum(String value) {
		return valueOf(value.toUpperCase());
	}
}
