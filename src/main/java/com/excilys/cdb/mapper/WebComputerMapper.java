package com.excilys.cdb.mapper;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import com.excilys.cdb.dto.WebComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.validator.ComputerValidator;

//id | name | introduced | discontinued | company_id
public class WebComputerMapper {
	
	static WebComputerMapper mapper;
	
	private WebComputerMapper() {
		
	}
	
	public static WebComputerMapper getInstance() {
		if (mapper == null)
			mapper = new WebComputerMapper();
		return mapper;
	}
	
	public WebComputerDTO toComputerDTO(Computer com) {
		return new WebComputerDTO.ComputerDTOBuilder(com.getId()+"", com.getName())
				.withIntroduced(com.getIntroduced().map(Date::valueOf).map(Date::toString).orElse(null))
				.withDiscontinued(com.getDiscontinued().map(Date::valueOf).map(Date::toString).orElse(null))
				.withCompany(WebCompanyMapper.getInstance().toCompanyDTO(com.getCompany()).orElse(null))
				.build();
	}
	
	public ArrayList<WebComputerDTO> toComputerDTOs(ArrayList<Computer> computers) {
		ArrayList<WebComputerDTO> dtos = new ArrayList<WebComputerDTO>();
		for (Computer com : computers) {
			dtos.add(toComputerDTO(com));
		}
		return dtos;
	}
	
	public Computer toComputer(WebComputerDTO dto) throws SQLException, IOException {
		ComputerValidator.getInstance().validate(dto);
		return new ComputerBuilder(Integer.parseInt(dto.getId()), dto.getName())
				.withIntroduced(dto.getIntroduced().map(LocalDate::parse).orElse(null))
				.withDiscontinued(dto.getDiscontinued().map(LocalDate::parse).orElse(null))
				.withCompany(WebCompanyMapper.getInstance().toCompany(dto.getCompany()).orElse(null))
				.build();
	}
}
