package com.excilys.cdb.persistence.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.dto.DBCompanyDTO;

// id | name
@Component
@Scope("singleton")
public class DBCompanyMapper {
	static final String COLONNE_COMPANY_ID = "company.id";
	
	static final String COLONNE_COMPANY_NAME = "company.name";
	
	private DBCompanyMapper() {
	}
	
	public Optional<DBCompanyDTO> toCompanyDTO(Optional<Company> com) {
		if (!com.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new DBCompanyDTO(com.get().getId(), com.get().getName()));
		}
	}
	
	public Optional<Company> toCompany(Optional<DBCompanyDTO> dto) {
		if (!dto.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new Company(dto.get().getId(), dto.get().getName()));
		}
	}
	
	public List<Company> toCompanyArray(List<DBCompanyDTO> dtos) {
		return dtos.stream().map(dto -> toCompany(Optional.of(dto)).get()).collect(Collectors.toList());
	}
}