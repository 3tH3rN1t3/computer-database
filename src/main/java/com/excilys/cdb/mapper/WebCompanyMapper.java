package com.excilys.cdb.mapper;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.dto.WebCompanyDTO;
import com.excilys.cdb.model.Company;

// id | name
//singleton
public class WebCompanyMapper {
	private static WebCompanyMapper mapper;
	
	private WebCompanyMapper() {
		
	}
	
	public static WebCompanyMapper getInstance() {
		if (mapper == null)
			mapper = new WebCompanyMapper();
		return mapper;
	}
	
	public Optional<WebCompanyDTO> toCompanyDTO(Optional<Company> com) {
		if (!com.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new WebCompanyDTO(com.get().getId()+"", com.get().getName()));
		}
	}
	
	public ArrayList<WebCompanyDTO> toCompanyDTOs(ArrayList<Company> companies) {
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
