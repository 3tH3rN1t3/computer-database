package com.excilys.cdb.web.mapper;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.web.dto.WebCompanyDTO;

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
}
