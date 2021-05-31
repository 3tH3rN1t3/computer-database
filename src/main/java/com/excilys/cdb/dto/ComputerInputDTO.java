package com.excilys.cdb.dto;

public class ComputerInputDTO {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private DBCompanyDTO company;
	
	private ComputerInputDTO (ComputerDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIntroduced() {
		return introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public DBCompanyDTO getCompany() {
		return company;
	}
	
	public static class ComputerDTOBuilder {
		private String id;
		private String name;
		private String introduced;
		private String discontinued;
		private DBCompanyDTO company;
		
		public ComputerDTOBuilder(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public ComputerDTOBuilder withIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public ComputerDTOBuilder withDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public ComputerDTOBuilder withCompany(DBCompanyDTO company) {
			this.company = company;
			return this;
		}
		
		public ComputerInputDTO build() {
			return new ComputerInputDTO(this);
		}
	}
	
	public String toString() {
		String info = "ID:" + id + " | Name: " + name;
		info += " | added: " + introduced;
		info += " | removed: " + discontinued;
		info += " | Company: " + company.getId() + " " + company.getName();
		return info;
	}
}
