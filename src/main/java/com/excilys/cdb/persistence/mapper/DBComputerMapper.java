package com.excilys.cdb.persistence.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dto.DBComputerDTO;

//id | name | introduced | discontinued | company_id
@Component
@Scope("singleton")
public class DBComputerMapper implements RowMapper<DBComputerDTO> {
	
	static final String COLONNE_ID = "computer.id";
	
	static final String COLONNE_NAME = "computer.name";
	
	static final String COLONNE_INTRODUCED = "introduced";
	
	static final String COLONNE_DISCONTINUED = "discontinued";
	
	private DBCompanyMapper companyMapper;
	
	private DBComputerMapper(DBCompanyMapper companyMapper) {
		this.companyMapper = companyMapper;
	}

	@Override
	public DBComputerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (rs.isAfterLast()) {
			return null;
		} else {
			return new DBComputerDTO.ComputerDTOBuilder(
					rs.getString(COLONNE_ID), rs.getString(COLONNE_NAME)
					)
					.withIntroduced(rs.getDate(COLONNE_INTRODUCED) == null ? null : rs.getDate(COLONNE_INTRODUCED).toString())
					.withDiscontinued(rs.getDate(COLONNE_DISCONTINUED) == null ? null : rs.getDate(COLONNE_DISCONTINUED).toString())
					.withCompany(companyMapper.mapRow(rs, rowNum))
					.build();
		}
	}
	
	public DBComputerDTO toComputerDTO(Computer com) {
		return new DBComputerDTO.ComputerDTOBuilder(com.getId()+"", com.getName())
				.withIntroduced(com.getIntroduced().map(Date::valueOf).map(Date::toString).orElse(null))
				.withDiscontinued(com.getDiscontinued().map(Date::valueOf).map(Date::toString).orElse(null))
				.withCompany(companyMapper.toCompanyDTO(com.getCompany()).orElse(null))
				.build();
	}
	
	public Optional<Computer> toComputer(Optional<DBComputerDTO> dto) {
		if (!dto.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(new Computer.ComputerBuilder(Integer.parseInt(dto.get().getId()), dto.get().getName())
				.withIntroduced(dto.get().getIntroduced().map(LocalDate::parse).orElse(null))
				.withDiscontinued(dto.get().getDiscontinued().map(LocalDate::parse).orElse(null))
				.withCompany(companyMapper.toCompany(dto.get().getCompany()).orElse(null))
				.build() );
	}
	
	public ArrayList<Computer> toComputerArray(List<DBComputerDTO> dtos) {
		ArrayList<Computer> coms = new ArrayList<Computer>();
		for (DBComputerDTO dto : dtos) {
			coms.add(toComputer(Optional.of(dto)).get());
		}
		return coms;
	}
}
