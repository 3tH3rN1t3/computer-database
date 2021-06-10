package com.excilys.cdb.persistence;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.dto.DBCompanyDTO;
import com.excilys.cdb.persistence.dto.DBComputerDTO;
import com.excilys.cdb.persistence.mapper.DBComputerMapper;

//id | name | introduced | discontinued | company_id
@Component
@Scope("singleton")
public class ComputerDAO {
	
	private static final String GET_COMPUTER_BY_ID_REQUEST = "SELECT computer.id, computer.name, "
			+ "introduced, discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE computer.id = ?";
	
	private static final String GET_COMPUTERS_BY_SEARCH_REQUEST = "SELECT computer.id, computer.name, "
			+ "introduced, discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE %s LIKE CONCAT('%%', ?, '%%') ORDER BY %s %s LIMIT ? OFFSET ?";
	
	private static final String INSERT_COMPUTER_REQUEST = "INSERT INTO computer "
			+ "(name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	
	private static final String UPDATE_COMPUTER_REQUEST = "UPDATE computer "
			+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
			+ "WHERE id = ?";
	
	private static final String DELETE_COMPUTER_REQUEST = "DELETE FROM computer WHERE id = ?";
	
	private static final String COUNT_COMPUTERS_BY_SEARCH_REQUEST = "SELECT COUNT(computer.id) AS count "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE %s LIKE CONCAT('%%', ?, '%%')";
	
	private JdbcTemplate template = new JdbcTemplate();
	
	@Autowired
	private DBComputerMapper mapper;
	
	private Logger logger = LogManager.getLogger(ComputerDAO.class);
	
	private ComputerDAO(DataSource datasource) {
		template.setDataSource(datasource);
	}
	
	public List<DBComputerDTO> search(Page p) {
		String query = String.format(GET_COMPUTERS_BY_SEARCH_REQUEST,p.getSearchBy().getColumn(), p.getOrderBy().getColumn(), p.getOrder());
		return template.query(query, mapper, p.getSearch(), p.getMaxItems(), (p.getNumPage()-1)*p.getMaxItems());
	}

	public Optional<DBComputerDTO> find(int id) {
		try {
			return Optional.of(template.query(GET_COMPUTER_BY_ID_REQUEST, mapper, id).get(0));
		} catch (IndexOutOfBoundsException e) {
			return Optional.empty();
		}
	}
	
	public int insertComputer(DBComputerDTO dbComputerDTO) {
		return template.update(INSERT_COMPUTER_REQUEST, dbComputerDTO.getName()
				, dbComputerDTO.getIntroduced().orElse(null)
				, dbComputerDTO.getDiscontinued().orElse(null)
				, dbComputerDTO.getCompany().map(DBCompanyDTO::getId).orElse(null));
	}
	
	public int updateComputer(DBComputerDTO dbComputerDTO) {
		return template.update(UPDATE_COMPUTER_REQUEST, dbComputerDTO.getName()
				, dbComputerDTO.getIntroduced().orElse(null)
				, dbComputerDTO.getDiscontinued().orElse(null)
				, dbComputerDTO.getCompany().map(DBCompanyDTO::getId).orElse(null)
				, dbComputerDTO.getId());
	}
	
	public int deleteComputer(int id) {
		return template.update(DELETE_COMPUTER_REQUEST, id);
	}
	
	public int CountComputers(Page page) {
		String query = String.format(COUNT_COMPUTERS_BY_SEARCH_REQUEST, page.getSearchBy().getColumn());
		return template.queryForObject(query, Integer.class, page.getSearch());
	}

}
