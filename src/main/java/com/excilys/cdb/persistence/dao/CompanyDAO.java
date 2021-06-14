package com.excilys.cdb.persistence.dao;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.persistence.dto.DBCompanyDTO;
import com.excilys.cdb.persistence.mapper.DBCompanyMapper;

@Component
@Scope("singleton")
public class CompanyDAO {
	
	private static final String GET_ALL_COMPANIES_REQUEST = "SELECT company.id, company.name FROM company";
	
	private static final String GET_COMPANY_BY_ID = "SELECT id, company.name FROM company WHERE id = ?";
	
	private static final String DELETE_COMPUTERS_REQUEST = "DELETE FROM computer WHERE company_id = ?";
	
	private static final String DELETE_COMPANY_REQUEST = "DELETE FROM company WHERE id = ?";
	
	private JdbcTemplate template = new JdbcTemplate();
	
	private DBCompanyMapper mapper;
	
	
	public CompanyDAO(DBCompanyMapper companyMapper, DataSource datasource) {
		this.mapper = companyMapper;
		template.setDataSource(datasource);
	}
	
	public List<DBCompanyDTO> getAllCompanies() {
		return template.query(GET_ALL_COMPANIES_REQUEST, mapper);
	}

	public Optional<DBCompanyDTO> getCompanyById(int id) {
		try {
			return Optional.of(template.query(GET_COMPANY_BY_ID, mapper, id).get(0));
		} catch (IndexOutOfBoundsException e) {
			return Optional.empty();
		}
	}
	
	@Transactional
	public int deleteCompany(int id) {
		int deletionCount = 0;
		deletionCount += template.update(DELETE_COMPUTERS_REQUEST, id);
		deletionCount += template.update(DELETE_COMPANY_REQUEST, id);
		return deletionCount;
	}
}
