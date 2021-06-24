package com.excilys.cdb.web.mapper;

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
	
	public Optional<WebComputerDTO> toComputerDTO(Optional<Computer> com) {
		if (!com.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new WebComputerDTO.WebComputerDTOBuilder(com.get().getId()+"", com.get().getName())
					.withIntroduced(com.get().getIntroduced().map(LocalDate::toString).orElse(null))
					.withDiscontinued(com.get().getDiscontinued().map(LocalDate::toString).orElse(null))
					.withCompanyId(com.get().getCompany().map(Company::getId).map(String::valueOf).orElse(null))
					.withCompanyName(com.get().getCompany().map(Company::getName).orElse(null))
					.build());
		}
	}
	
	public WebComputerDTO toComputerDTO(Computer com) {
		return toComputerDTO(Optional.of(com)).get();
	}
	
	public List<WebComputerDTO> toComputerDTOArray(List<Computer> computers) {
		return computers.parallelStream().map(com -> toComputerDTO(com)).collect(Collectors.toList());
	}
	
	public Computer toComputer(WebComputerDTO dto) {
		return new Computer.ComputerBuilder(Integer.parseInt(dto.getId()), dto.getName())
				.withIntroduced(Optional.ofNullable(dto.getIntroduced()).filter(string -> !string.isBlank()).map(LocalDate::parse).orElse(null))
				.withDiscontinued(Optional.ofNullable(dto.getDiscontinued()).filter(string -> !string.isBlank()).map(LocalDate::parse).orElse(null))
				.withCompany(dto.getCompanyId().isEmpty() ? null : new Company(Integer.parseInt(dto.getCompanyId()), dto.getCompanyName()))
				.build();
	}
}
