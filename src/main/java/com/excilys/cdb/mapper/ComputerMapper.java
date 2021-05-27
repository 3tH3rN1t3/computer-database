package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

//id | name | introduced | discontinued | company_id
public class ComputerMapper {
	
	private static final String COLONNE_ID = "id";
	
	private static final String COLONNE_NAME = "name";
	
	private static final String COLONNE_INTRODUCED = "introduced";
	
	private static final String COLONNE_DISCONTINUED = "discontinued";
	
	private static final String COLONNE_COMPANY_ID = "company_id";
	
	private static final String COLONNE_COMPANY_NAME = "company.name";
	
	private static ComputerMapper mapper;
	
	public static ComputerMapper getInstance() {
		if (mapper == null)
			mapper = new ComputerMapper();
		return mapper;
	}
	
	public ArrayList<ComputerDTO> toComputerDTOs(ResultSet rs) {
		ArrayList<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		while(true) {
			Optional<ComputerDTO> com = toComputerDTO(rs);
			if(com.isPresent()) {
				computers.add(com.get());
			} else {
				break;
			}
		}
		return computers;
	}
	
	public Optional<ComputerDTO> toComputerDTO(ResultSet rs) {
		try {
			rs.next();
			return Optional.of(new ComputerDTO.ComputerDTOBuilder(
					rs.getString(COLONNE_ID), rs.getString(COLONNE_NAME)
					)
					.withIntroduced(rs.getDate(COLONNE_INTRODUCED) == null ? null : rs.getDate(COLONNE_INTRODUCED).toString())
					.withDiscontinued(rs.getDate(COLONNE_DISCONTINUED) == null ? null : rs.getDate(COLONNE_DISCONTINUED).toString())
					.withCompanyId(rs.getString(COLONNE_COMPANY_ID))
					.withCompanyName(rs.getString(COLONNE_COMPANY_NAME))
					.build() );
		} catch (SQLException e) {
			//TODO log
		}
		return Optional.empty();
	}
	
	public ArrayList<Computer> toComputers(ArrayList<ComputerDTO> dtos) {
		ArrayList<Computer> coms = new ArrayList<Computer>();
		for (ComputerDTO dto : dtos) {
			coms.add(toComputer(Optional.of(dto)));
		}
		return coms;
	}
	
	public Computer toComputer(Optional<ComputerDTO> dto) {
		if (!dto.isPresent()) {
			return null;
		}
		return new Computer.ComputerBuilder(Integer.parseInt(dto.get().getId()), dto.get().getName())
				.withIntroduced(dto.get().getIntroduced() == null ? null : LocalDate.parse(dto.get().getIntroduced()))
				.withDiscontinued(dto.get().getDiscontinued() == null ? null : LocalDate.parse(dto.get().getDiscontinued()))
				.withCompany(dto.get().getCompnyId() == null ? null : new Company(
						Integer.parseInt(dto.get().getCompnyId()), dto.get().getCompanyName()))
				.build();
	}
}
