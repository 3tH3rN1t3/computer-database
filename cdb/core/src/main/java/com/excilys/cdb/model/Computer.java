package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;


public class Computer {
	
	private int id;
	
	private String name;
	
	private LocalDate introduced;
	
	private LocalDate discontinued;
	
	private Company company;
	
	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}
	
	public int getId() {
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
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		info += " | added: ";
		if (introduced != null) {
			info += introduced;
		} else {
			info += "-";
		}
		info += " | removed: ";
		if (discontinued != null) {
			info += discontinued;
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
	
	public static class ComputerBuilder {
		private int id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		public ComputerBuilder(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public ComputerBuilder withIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public ComputerBuilder withDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public ComputerBuilder withCompany(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
	}
}
