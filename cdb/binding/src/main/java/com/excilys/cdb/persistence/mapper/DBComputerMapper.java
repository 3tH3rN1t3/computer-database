package com.excilys.cdb.persistence.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dto.DBComputerDTO;

//id | name | introduced | discontinued | company_id
@Component
@Scope("singleton")
public class DBComputerMapper {
	
	private DBCompanyMapper companyMapper;
	
	private DBComputerMapper(DBCompanyMapper companyMapper) {
		this.companyMapper = companyMapper;
	}
	
	public DBComputerDTO toComputerDTO(Computer com) {
		return new DBComputerDTO.ComputerDTOBuilder(com.getId(), com.getName())
				.withIntroduced(com.getIntroduced().map(LocalDate::toString).orElse(null))
				.withDiscontinued(com.getDiscontinued().map(LocalDate::toString).orElse(null))
				.withCompany(companyMapper.toCompanyDTO(com.getCompany()).orElse(null))
				.build();
	}
	
	public Optional<Computer> toComputer(Optional<DBComputerDTO> dto) {
		if (!dto.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(new Computer.ComputerBuilder(dto.get().getId(), dto.get().getName())
				.withIntroduced(Optional.ofNullable(dto.get().getIntroduced()).map(LocalDate::parse).orElse(null))
				.withDiscontinued(Optional.ofNullable(dto.get().getDiscontinued()).map(LocalDate::parse).orElse(null))
				.withCompany(companyMapper.toCompany(Optional.ofNullable(dto.get().getCompany())).orElse(null))
				.build() );
	}
	
	public List<Computer> toComputerArray(List<DBComputerDTO> dtos) {
		return dtos.stream().map(dto -> toComputer(Optional.of(dto)).get()).collect(Collectors.toList());
	}
}