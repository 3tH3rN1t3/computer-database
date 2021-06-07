package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.WebComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validator.ComputerValidator;

//id | name | introduced | discontinued | company_id
@Component
@Scope("singleton")
public class WebComputerMapper {
	
	@Autowired
	private WebCompanyMapper companyMapper;
	
	@Autowired
	private ComputerValidator validator;
	
	private WebComputerMapper() {
	}
	
	public WebComputerDTO toComputerDTO(Computer com) {
		return new WebComputerDTO.ComputerDTOBuilder(com.getId()+"", com.getName())
				.withIntroduced(com.getIntroduced().map(Date::valueOf).map(Date::toString).orElse(null))
				.withDiscontinued(com.getDiscontinued().map(Date::valueOf).map(Date::toString).orElse(null))
				.withCompany(companyMapper.toCompanyDTO(com.getCompany()).orElse(null))
				.build();
	}
	
	public ArrayList<WebComputerDTO> toComputerDTOArray(ArrayList<Computer> computers) {
		ArrayList<WebComputerDTO> dtos = new ArrayList<WebComputerDTO>();
		for (Computer com : computers) {
			dtos.add(toComputerDTO(com));
		}
		return dtos;
	}
	
	public Computer toComputer(WebComputerDTO dto) throws SQLException {
		validator.validate(dto);
		
		return new Computer.ComputerBuilder(Integer.parseInt(dto.getId()), dto.getName())
				.withIntroduced(dto.getIntroduced().map(LocalDate::parse).orElse(null))
				.withDiscontinued(dto.getDiscontinued().map(LocalDate::parse).orElse(null))
				.withCompany(companyMapper.toCompany(dto.getCompany()).orElse(null))
				.build();
	}
}
