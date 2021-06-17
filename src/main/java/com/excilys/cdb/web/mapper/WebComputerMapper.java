package com.excilys.cdb.web.mapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.web.dto.WebComputerDTO;

//id | name | introduced | discontinued | company_id
@Component
@Scope("singleton")
public class WebComputerMapper {
	
	private WebComputerMapper() {
	}
	
	public WebComputerDTO toComputerDTO(Computer com) {
		return new WebComputerDTO.WebComputerDTOBuilder(com.getId()+"", com.getName())
				.withIntroduced(Optional.ofNullable(com.getIntroduced()).map(Date::valueOf).map(Date::toString).orElse(null))
				.withDiscontinued(Optional.ofNullable(com.getDiscontinued()).map(Date::valueOf).map(Date::toString).orElse(null))
				.withCompanyId(Optional.ofNullable(com.getCompany()).map(Company::getId).map(String::valueOf).orElse(null))
				.withCompanyName(Optional.ofNullable(com.getCompany()).map(Company::getName).orElse(null))
				.build();
	}
	
	public List<WebComputerDTO> toComputerDTOArray(List<Computer> computers) {
		return computers.parallelStream().map(com -> toComputerDTO(com)).collect(Collectors.toList());
	}
	
	public Computer toComputer(WebComputerDTO dto) {
		return new Computer.ComputerBuilder(Integer.parseInt(dto.getId()), dto.getName())
				.withIntroduced(Optional.ofNullable(dto.getIntroduced()).filter(string -> !string.isBlank()).map(LocalDate::parse).orElse(null))
				.withDiscontinued(Optional.ofNullable(dto.getDiscontinued()).filter(string -> !string.isBlank()).map(LocalDate::parse).orElse(null))
				.withCompany(dto.getCompanyId() == "" ? null : new Company(Integer.parseInt(dto.getCompanyId()), dto.getCompanyName()))
				.build();
	}
}
