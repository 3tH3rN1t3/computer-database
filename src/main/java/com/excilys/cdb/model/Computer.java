package com.excilys.cdb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Computer {
	private String name;
	private int id;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	public Computer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Computer(int id, String name, LocalDate addDate, LocalDate removeDate, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = addDate;
		this.discontinued = removeDate;
		this.company = company;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIntroduced(LocalDate addDate) {
		this.introduced = addDate;
	}
	
	public void setDiscontinued(LocalDate removeDate) {
		this.discontinued = removeDate;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Optional<LocalDate> getIntroduced() {
		return Optional.ofNullable(introduced);
	}
	
	public Optional<LocalDate> getDiscontinued() {
		return Optional.ofNullable(discontinued);
	}
	
	public Optional<Company> getCompany() {
		return Optional.ofNullable(company);
	}
	
	public String toString() {
		String info = "ID:" + id + " | Name: " + name;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		info += " | added: ";
		if (introduced != null) {
			info += introduced.format(formatter);
		} else {
			info += "-";
		}
		info += " | removed: ";
		if (discontinued != null) {
			info += discontinued.format(formatter);
		} else {
			info += "-";
		}
		info += " | Company: ";
		if (company != null) {
			info += "{" + company + "}";
		} else {
			info += "-";
		}
		return info;
	}
}
