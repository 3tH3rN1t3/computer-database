package com.excilys.cdb.dto;

public class ComputerSQLDTO {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyId;
	private String companyName;
	
	public ComputerSQLDTO (ComputerSQLDTOBuilder builder) {
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

	@Override
	public String toString() {
		return "ComputerDTO [id:" + id + ", name:" + name +
				", introduced:" + introduced + ", discontinued:" + discontinued +
				", companyId:" + companyId + ", companyName:" + companyName + "]";
	}
	
	public static class ComputerSQLDTOBuilder {
		private String id;
		private String name;
		private String introduced;
		private String discontinued;
		private String companyId;
		private String companyName;
		
		public ComputerSQLDTOBuilder(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public ComputerSQLDTOBuilder withIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public ComputerSQLDTOBuilder withDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public ComputerSQLDTOBuilder withCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}
		
		public ComputerSQLDTOBuilder withCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public ComputerSQLDTO build() {
			return new ComputerSQLDTO(this);
		}
	}
}
