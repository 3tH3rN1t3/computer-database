package com.excilys.cdb.model;

public enum SearchBy {
	NAME, INTRODUCED, DISCONTINUED, COMPANY;
	
	public static SearchBy getEnum(String value) {
		return valueOf(value.toUpperCase());
	}
}
