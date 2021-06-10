package com.excilys.cdb.persistence.dto;

import java.util.Optional;

public class DBComputerDTO {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private DBCompanyDTO company;
	
	private DBComputerDTO(ComputerDTOBuilder builder) {
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
	
	public Optional<String> getIntroduced() {
		return Optional.ofNullable(introduced);
	}
	
	public Optional<String> getDiscontinued() {
		return Optional.ofNullable(discontinued);
	}
	
	public Optional<DBCompanyDTO> getCompany() {
		return Optional.ofNullable(company);
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
		
		public DBComputerDTO build() {
			return new DBComputerDTO(this);
		}
	}
}
