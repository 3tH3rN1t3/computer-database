package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.excilys.cdb.persistence.converter.LocalDateConverter;


@Entity
@Table(name = "computer")
public class Computer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Convert(converter = LocalDateConverter.class)
	private String introduced;

	@Convert(converter = LocalDateConverter.class)
	private String discontinued;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "company_id")
	private Company company;
	
	private Computer() {}
	
	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		System.out.println(builder.introduced.orElse(null));
		this.introduced = builder.introduced.map(LocalDate::toString).orElse(null);
		this.discontinued = builder.discontinued.map(LocalDate::toString).orElse(null);
		this.company = builder.company;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced.toString();
	}
	
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued.toString();
	}
	
	public void setCompany(Company company) {
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
	
	public LocalDate getIntroducedDate() {
		return LocalDate.parse(introduced);
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public LocalDate getDiscontinuedDate() {
		return LocalDate.parse(discontinued);
	}
	
	public Company getCompany() {
		return company;
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
		private Optional<LocalDate> introduced = Optional.empty();
		private Optional<LocalDate> discontinued = Optional.empty();
		private Company company;
		
		public ComputerBuilder(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public ComputerBuilder withIntroduced(LocalDate introduced) {
			this.introduced = Optional.ofNullable(introduced);
			return this;
		}
		
		public ComputerBuilder withDiscontinued(LocalDate discontinued) {
			this.discontinued = Optional.ofNullable(discontinued);
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
