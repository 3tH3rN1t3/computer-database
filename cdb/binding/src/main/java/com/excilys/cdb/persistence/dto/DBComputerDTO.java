package com.excilys.cdb.persistence.dto;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.cdb.persistence.converter.LocalDateConverter;

@Entity
@Table(name = "computer")
public class DBComputerDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Convert(converter=LocalDateConverter.class)
	private String introduced;

	@Convert(converter=LocalDateConverter.class)
	private String discontinued;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private DBCompanyDTO company;
	
	private DBComputerDTO() {}
	
	private DBComputerDTO(ComputerDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}
	
	private void setId (int id) {
		this.id = id;
	}
	
	private void setName (String name) {
		this.name = name;
	}
	
	private void setIntroduced (String introduced) {
		this.introduced = introduced;
	}
	
	private void setDiscontinued (String discontinued) {
		this.discontinued = discontinued;
	}
	
	private void setCompany (DBCompanyDTO company) {
		this.company = company;
	}
	
	public int getId() {
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
		private int id;
		private String name;
		private String introduced;
		private String discontinued;
		private DBCompanyDTO company;
		
		public ComputerDTOBuilder(int id, String name) {
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