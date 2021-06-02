package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.dto.DBCompanyDTO;
import com.excilys.cdb.model.Company;

// id | name
//singleton
public class DBCompanyMapper {
	static final String COLONNE_COMPANY_ID = "company.id";
	
	static final String COLONNE_COMPANY_NAME = "company.name";

	private static DBCompanyMapper mapper;
	
	private DBCompanyMapper() {
		
	}
	
	public static DBCompanyMapper getInstance() {
		if (mapper == null)
			mapper = new DBCompanyMapper();
		return mapper;
	}
	
	public Optional<DBCompanyDTO> toCompanyDTO(ResultSet rs) {
		try {
			if (rs.isBeforeFirst()) {
				rs.next();
			}
			if (rs.getString(COLONNE_COMPANY_ID) != null) {
				return Optional.of(new DBCompanyDTO(rs.getString(COLONNE_COMPANY_ID), rs.getString(COLONNE_COMPANY_NAME)));
			} else {
				return Optional.empty();
			}
		} catch (SQLException e) {
			return Optional.empty();
		}
	}
	
	public Optional<DBCompanyDTO> toCompanyDTO(Optional<Company> com) {
		if (!com.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new DBCompanyDTO(com.get().getId()+"", com.get().getName()));
		}
	}
	
	public ArrayList<DBCompanyDTO> toCompanyDTOs(ResultSet rs) throws SQLException {
		ArrayList<DBCompanyDTO> companies = new ArrayList<DBCompanyDTO>();
		while(rs.next()) {
			Optional<DBCompanyDTO> com = toCompanyDTO(rs);
			if(com.isPresent()) {
				companies.add(com.get());
			} else {
				break;
			}
		}
		return companies;
	}
	
	public Optional<Company> toCompany(Optional<DBCompanyDTO> dto) {
		if (!dto.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(new Company(Integer.parseInt(dto.get().getId()), dto.get().getName()));
		}
	}
	
	public ArrayList<Company> toCompanies(ArrayList<DBCompanyDTO> dtos) throws SQLException {
		ArrayList<Company> companies = new ArrayList<Company>();
		for (DBCompanyDTO dto : dtos) {
			companies.add(toCompany(Optional.of(dto)).get());
		}
		return companies;
	}
}
