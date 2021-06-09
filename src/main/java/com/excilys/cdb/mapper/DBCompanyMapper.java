package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DBCompanyDTO;
import com.excilys.cdb.model.Company;

// id | name
@Component
@Scope("singleton")
public class DBCompanyMapper implements RowMapper<DBCompanyDTO> {
	static final String COLONNE_COMPANY_ID = "company.id";
	
	static final String COLONNE_COMPANY_NAME = "company.name";
	
	private DBCompanyMapper() {
	}
	
	public DBCompanyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (rs.isAfterLast() || rs.getString(COLONNE_COMPANY_ID) == null) {
			return null;
		} else {
			return new DBCompanyDTO(rs.getString(COLONNE_COMPANY_ID), rs.getString(COLONNE_COMPANY_NAME));
		}
	}
	
	public Optional<DBCompanyDTO> toCompanyDTO(Optional<Company> com) {
		if (!com.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new DBCompanyDTO(com.get().getId()+"", com.get().getName()));
		}
	}
	
	public Optional<Company> toCompany(Optional<DBCompanyDTO> dto) {
		if (!dto.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new Company(Integer.parseInt(dto.get().getId()), dto.get().getName()));
		}
	}
	
	public ArrayList<Company> toCompanyArray(List<DBCompanyDTO> dtos) throws SQLException {
		ArrayList<Company> companies = new ArrayList<Company>();
		for (DBCompanyDTO dto : dtos) {
			companies.add(toCompany(Optional.of(dto)).get());
		}
		return companies;
	}
}
