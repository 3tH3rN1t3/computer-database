package com.excilys.cdb.persistence.dto;

public class DBCompanyDTO {
	private String id;
	private String name;
	
	public DBCompanyDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
