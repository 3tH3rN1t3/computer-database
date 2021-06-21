package com.excilys.cdb.persistence.repository;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.logger.LoggerCDB;

@Component
public class CompanyDAO {
	
	private static final String DELETE_COMPANY_REQUEST = "DELETE FROM company WHERE id = ?";
	private static final String DELETE_COMPANY_COMPUTERS_RREQUEST = "DELETE FROM computer WHERE company_id=?;";
	
	JdbcTemplate jdbcTemplate;
	
	public CompanyDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource) ;
	}

	@Transactional(rollbackFor = DataAccessException.class)
	public int deleteById(int id) {
		int deletions = 0;
		try {
			deletions += jdbcTemplate.update(DELETE_COMPANY_COMPUTERS_RREQUEST, id);
			deletions += jdbcTemplate.update(DELETE_COMPANY_REQUEST, id);
		} catch (DataAccessException e) {
			LoggerCDB.logger.error("Error deleting Company with id: " + id, e);
			throw e;
		}
		return deletions;
	}
}
