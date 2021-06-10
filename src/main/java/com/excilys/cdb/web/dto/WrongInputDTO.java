package com.excilys.cdb.web.dto;

public class WrongInputDTO {
	private Boolean id = false;
	
	private Boolean name = false;
	
	private Boolean introduced = false;
	
	private Boolean discontinued = false;
	
	private Boolean interval = false;
	
	private Boolean company = false;
	
	public void setId(Boolean bool) {
		id = bool;
	}
	
	public void setName(Boolean bool) {
		name = bool;
	}
	
	public void setIntroduced(Boolean bool) {
		introduced = bool;
	}
	
	public void setDiscontinued(Boolean bool) {
		discontinued = bool;
	}
	
	public void setInterval(Boolean bool) {
		interval = bool;
	}
	
	public void setCompany(Boolean bool) {
		company = bool;
	}
	
	public Boolean getId() {
		return id;
	}
	
	public Boolean getName() {
		return name;
	}
	
	public Boolean getIntroduced() {
		return introduced;
	}
	
	public Boolean getDiscontinued() {
		return discontinued;
	}
	
	public Boolean getInterval() {
		return interval;
	}
	
	public Boolean getCompany() {
		return company;
	}
}
