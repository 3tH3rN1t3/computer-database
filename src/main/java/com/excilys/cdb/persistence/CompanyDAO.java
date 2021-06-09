package com.excilys.cdb.persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dto.DBCompanyDTO;
import com.excilys.cdb.mapper.DBCompanyMapper;

@Component
@Scope("singleton")
public class CompanyDAO {
	
	private static final String GET_ALL_COMPANIES_REQUEST = "SELECT company.id, company.name FROM company";
	
	private static final String GET_COMPANY_BY_ID = "SELECT id, company.name FROM company WHERE id = ?";
	
	private static final String DELETE_COMPUTERS_REQUEST = "DELETE FROM computer WHERE company_id = ?";
	
	private static final String DELETE_COMPANY_REQUEST = "DELETE FROM company WHERE id = ?";
	
	private JdbcTemplate template = new JdbcTemplate();
	
	@Autowired
	private DBCompanyMapper mapper;
	
	private Logger logger = LogManager.getLogger(getClass());;
	
	
	public CompanyDAO(DataSource datasource) {
		template.setDataSource(datasource);
	}
	
	public List<DBCompanyDTO> getAllCompanies() throws SQLException {
		return template.query(GET_ALL_COMPANIES_REQUEST, mapper);
	}

	public Optional<DBCompanyDTO> getCompanyById(int id) throws SQLException{
		return Optional.ofNullable(template.queryForObject(GET_COMPANY_BY_ID, mapper, id));
	}
	
	@Transactional
	public int deleteCompany(int id) throws SQLException {
		int deletionCount = 0;
		deletionCount += template.update(DELETE_COMPUTERS_REQUEST, id);
		deletionCount += template.update(DELETE_COMPANY_REQUEST, id);
		return deletionCount;
	}
}
