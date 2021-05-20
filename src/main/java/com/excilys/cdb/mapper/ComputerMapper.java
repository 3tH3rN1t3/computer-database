package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

//id | name | introduced | discontinued | company_id
public class ComputerMapper {
	private static ComputerMapper mapper;
	
	public static ComputerMapper getMapper() {
		if (mapper == null)
			mapper = new ComputerMapper();
		return mapper;
	}
	
	public ArrayList<Computer> map(ResultSet rs) throws SQLException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		while(true) {
			Optional<Computer> com = mapOne(rs);
			if(com.isPresent()) {
				computers.add(com.get());
			} else {
				break;
			}
		}
		return computers;
	}
	
	public Optional<Computer> mapOne(ResultSet rs) throws SQLException {
		if (rs.next()) {
			LocalDate addDate = null;
			LocalDate removeDate = null;
			Company company = null;
			if (rs.getDate("introduced") != null) {
				addDate = rs.getDate("introduced").toLocalDate();
			}
			
			if (rs.getDate("discontinued") != null) {
				removeDate = rs.getDate("discontinued").toLocalDate();
			}
			
			if (rs.getInt("company.id") != 0) {
				company = new Company(rs.getInt("company.id"), rs.getString("company.name"));
			}
			
			return Optional.of(new Computer(rs.getInt("id"), rs.getString("name"), addDate, removeDate, company));
		} else {
			return Optional.empty();
		}
	}
}
