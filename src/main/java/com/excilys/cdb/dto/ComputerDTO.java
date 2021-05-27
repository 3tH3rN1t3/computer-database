package com.excilys.cdb.dto;

public class ComputerDTO {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyId;
	private String companyName;
	
	public ComputerDTO (ComputerDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyId = builder.companyId;
		this.companyName = builder.companyName;
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
	
	public String getCompnyId() {
		return companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public static class ComputerDTOBuilder {
		private String id;
		private String name;
		private String introduced;
		private String discontinued;
		private String companyId;
		private String companyName;
		
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
		
		public ComputerDTOBuilder withCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}
		
		public ComputerDTOBuilder withCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public ComputerDTO build() {
			return new ComputerDTO(this);
		}
	}
}
