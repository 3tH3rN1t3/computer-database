package com.excilys.cdb.web.dto;

import org.springframework.format.annotation.DateTimeFormat;

public class WebComputerDTO {
	
	private String id;
	
	private String name;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String introduced;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String discontinued;
	
	private String companyId;
	
	private String companyName;
	
	public WebComputerDTO () {
	}
	
	private WebComputerDTO(WebComputerDTOBuilder builder) {
		id = builder.id;
		name = builder.name;
		introduced = builder.introduced;
		discontinued = builder.discontinued;
		companyId = builder.companyId;
		companyName = builder.companyName;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	
	public String getIntroduced() {
		return introduced;
	}
	
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public static class WebComputerDTOBuilder {
		String id;
		String name;
		String introduced;
		String discontinued;
		String companyId;
		String companyName;
		
		public WebComputerDTOBuilder(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public WebComputerDTOBuilder withIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public WebComputerDTOBuilder withDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public WebComputerDTOBuilder withCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}
		
		public WebComputerDTOBuilder withCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public WebComputerDTO build() {
			return new WebComputerDTO(this);
		}
	}
}
