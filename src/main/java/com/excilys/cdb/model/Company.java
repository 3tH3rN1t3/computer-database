package com.excilys.cdb.model;

public class Company {
	private int id;
	private String name;
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return "ID:" + id + " | Name: " + name;
	}
}
