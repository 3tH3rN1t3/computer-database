package com.excilys.cdb.mapper;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.WebCompanyDTO;
import com.excilys.cdb.model.Company;

// id | name
@Component
@Scope("singleton")
public class WebCompanyMapper {
	
	private WebCompanyMapper() {
	}
	
	public Optional<WebCompanyDTO> toCompanyDTO(Optional<Company> com) {
		if (!com.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new WebCompanyDTO(com.get().getId()+"", com.get().getName()));
		}
	}
	
	public ArrayList<WebCompanyDTO> toCompanyDTOArray(ArrayList<Company> companies) {
		ArrayList<WebCompanyDTO> dtos = new ArrayList<WebCompanyDTO>();
		for (Company company : companies) {
			dtos.add(toCompanyDTO(Optional.of(company)).get());
		}
		return dtos;
	}
	
	public Optional<Company> toCompany(Optional<WebCompanyDTO> dto) {
		if (!dto.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new Company(Integer.parseInt(dto.get().getId()), dto.get().getName()));
		}
	}
}
