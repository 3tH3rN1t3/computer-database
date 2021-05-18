package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Computer {
	private String name;
	private int id;
	private LocalDate addDate;
	private LocalDate removeDate;
	private Company company;
	
	public Computer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Computer(int id, String name, LocalDate addDate, LocalDate removeDate, Company company) {
		this.id = id;
		this.name = name;
		this.addDate = addDate;
		this.removeDate = removeDate;
		this.company = company;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddDate(LocalDate addDate) {
		this.addDate = addDate;
	}
	
	public void setRemoveDate(LocalDate removeDate) {
		this.removeDate = removeDate;
	}
	
	public void setCompanyID(Company company) {
		this.company = company;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Optional<LocalDate> getAddDate() {
		return Optional.ofNullable(addDate);
	}
	
	public Optional<LocalDate> getRemoveDate() {
		return Optional.ofNullable(removeDate);
	}
	
	public Optional<Company> getCompany() {
		return Optional.ofNullable(company);
	}
	
	public String toString() {
		String info = "ID:" + id + " | Name: " + name;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		info += " | added: ";
		if (addDate != null) {
			info += addDate.format(formatter);
		} else {
			info += "-";
		}
		info += " | removed: ";
		if (removeDate != null) {
			info += removeDate.format(formatter);
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
