package com.excilys.cdb.dto;

public class WebCompanyDTO {
	private String id;
	private String name;
	
	public WebCompanyDTO(String id, String name) {
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
