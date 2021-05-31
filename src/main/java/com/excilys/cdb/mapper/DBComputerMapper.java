package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.dto.DBComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validator.ComputerValidator;

//id | name | introduced | discontinued | company_id
public class DBComputerMapper {
	
	static final String COLONNE_ID = "id";
	
	static final String COLONNE_NAME = "name";
	
	static final String COLONNE_INTRODUCED = "introduced";
	
	static final String COLONNE_DISCONTINUED = "discontinued";
	
	static DBComputerMapper mapper;
	
	public static DBComputerMapper getInstance() {
		if (mapper == null)
			mapper = new DBComputerMapper();
		return mapper;
	}
	
	public Optional<DBComputerDTO> toComputerDTO(ResultSet rs) {
		try {
			return Optional.of(new DBComputerDTO.ComputerDTOBuilder(
					rs.getString(COLONNE_ID), rs.getString(COLONNE_NAME)
					)
					.withIntroduced(rs.getDate(COLONNE_INTRODUCED) == null ? null : rs.getDate(COLONNE_INTRODUCED).toString())
					.withDiscontinued(rs.getDate(COLONNE_DISCONTINUED) == null ? null : rs.getDate(COLONNE_DISCONTINUED).toString())
					.withCompany(DBCompanyMapper.getMapper().toCompanyDTO(rs).orElse(null))
					.build() );
		} catch(SQLException e) {
			return Optional.empty();
		}
	}
	
	public DBComputerDTO toComputerDTO(Computer com) {
		return new DBComputerDTO.ComputerDTOBuilder(com.getID()+"", com.getName())
				.withIntroduced(com.getIntroduced().map(Date::valueOf).map(Date::toString).orElse(null))
				.withIntroduced(com.getDiscontinued().map(Date::valueOf).map(Date::toString).orElse(null))
				.withCompany(DBCompanyMapper.getMapper().toCompanyDTO(com.getCompany()).orElse(null))
				.build();
	}
	
	public ArrayList<DBComputerDTO> toComputerDTOs(ResultSet rs) throws SQLException {
		ArrayList<DBComputerDTO> computers = new ArrayList<DBComputerDTO>();
		while(rs.next()) {
			Optional<DBComputerDTO> com = toComputerDTO(rs);
			if(com.isPresent()) {
				computers.add(com.get());
			} else {
				break;
			}
		}
		return computers;
	}
	
	public Optional<Computer> toComputer(Optional<DBComputerDTO> dto) {
		if (!dto.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(new Computer.ComputerBuilder(Integer.parseInt(dto.get().getId()), dto.get().getName())
				.withIntroduced(dto.get().getIntroduced().map(LocalDate::parse).orElse(null))
				.withDiscontinued(dto.get().getDiscontinued().map(LocalDate::parse).orElse(null))
				.withCompany(DBCompanyMapper.getMapper().toCompany(dto.get().getCompany()).orElse(null))
				.build() );
	}
	
	public ArrayList<Computer> toComputers(ArrayList<DBComputerDTO> dtos) {
		ArrayList<Computer> coms = new ArrayList<Computer>();
		for (DBComputerDTO dto : dtos) {
			coms.add(toComputer(Optional.of(dto)).get());
		}
		return coms;
	}
}
