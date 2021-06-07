package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DBCompanyDTO;
import com.excilys.cdb.dto.DBComputerDTO;
import com.excilys.cdb.mapper.DBComputerMapper;
import com.excilys.cdb.model.Page;

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
	
	private static final String DELETE_COMPUTER_BUNCH_REQUEST = "DELETE FROM computer WHERE company_id = ?";
	
	private static final String COUNT_COMPUTERS_BY_SEARCH_REQUEST = "SELECT COUNT(computer.id) AS count "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE %s LIKE CONCAT('%%', ?, '%%')";

	@Autowired
	private Database database;
	
	@Autowired
	private DBComputerMapper mapper;
	
	private Logger logger = LogManager.getLogger(ComputerDAO.class);
	
	private ComputerDAO() {
	}
	
	public ArrayList<DBComputerDTO> search(Page p) throws SQLException {
		ArrayList<DBComputerDTO> coms = new ArrayList<DBComputerDTO>();
		try (Connection conn = database.getConnection();) {
			
			PreparedStatement stmt = conn.prepareStatement(String.format(GET_COMPUTERS_BY_SEARCH_REQUEST,p.getSearchBy().getColumn(), p.getOrderBy().getColumn(), p.getOrder()));
			stmt.setString(1, p.getSearch());
			stmt.setInt(2, p.getMaxItems());
			stmt.setInt(3, (p.getNumPage()-1)*p.getMaxItems());
			ResultSet rs = stmt.executeQuery();
			coms = mapper.toComputerDTOArray(rs);
			
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.search", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Retreived " + coms.size() + " lines from the database\n");
		return coms;
	}

	public Optional<DBComputerDTO> find(int id) throws SQLException {
		Optional<DBComputerDTO> com = Optional.empty();
		try (Connection conn = database.getConnection();) {

			PreparedStatement stmt = conn.prepareStatement(GET_COMPUTER_BY_ID_REQUEST);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			com = mapper.toComputerDTO(rs);
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.find", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		if (com.isPresent()) {
			logger.info("Retreived computer with ID " + id + " from the database\n");
		} else {
			logger.info("Failed to retreive computer with ID " + id + " from the database\n");
		}
		return com;
	}
	
	public int insertComputer(DBComputerDTO dbComputerDTO) throws SQLException {
		int id = 0;
		try (Connection conn = database.getConnection();) {

			PreparedStatement stmt = conn.prepareStatement(INSERT_COMPUTER_REQUEST, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, dbComputerDTO.getName());
			stmt.setString(2, dbComputerDTO.getIntroduced().orElse(null));
			stmt.setString(3, dbComputerDTO.getDiscontinued().orElse(null));
			stmt.setString(4, dbComputerDTO.getCompany().map(DBCompanyDTO::getId).orElse(null));
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.first();
			id = rs.getInt(1);
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.insertComputer", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Inserted computer with ID " + id + " into the database\n");
		return id;
	}
	
	public int updateComputer(DBComputerDTO dbComputerDTO) throws SQLException {
		int edits = 0;
		try (Connection conn = database.getConnection();) {

			PreparedStatement stmt = conn.prepareStatement(UPDATE_COMPUTER_REQUEST);
			stmt.setString(1, dbComputerDTO.getName());
			stmt.setString(2, dbComputerDTO.getIntroduced().orElse(null));
			stmt.setString(3, dbComputerDTO.getDiscontinued().orElse(null));
			stmt.setString(4, dbComputerDTO.getCompany().map(DBCompanyDTO::getId).orElse(null));
			stmt.setString(5, dbComputerDTO.getId());
			edits = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.updateComputer", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		if (edits == 1) {
			logger.info("Updated computer with ID " + dbComputerDTO.getId() + " in the database");
		} else {
			logger.info("Could not update computer with ID " + dbComputerDTO.getId());
		}
		return edits;
	}
	
	public int deleteComputer(int id) throws SQLException {
		int deletes = 0;
		try (Connection conn = database.getConnection();) {

			PreparedStatement stmt = conn.prepareStatement(DELETE_COMPUTER_REQUEST);
			stmt.setInt(1, id);
			deletes = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.deleteComputer", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Deleted computer with ID " + id + " from the database\n");
		return deletes;
	}
	
	public int deleteComputerBunch(int companyId, Connection conn) throws SQLException {
		try {
			PreparedStatement stmt = conn.prepareStatement(DELETE_COMPUTER_BUNCH_REQUEST);
			stmt.setInt(1, companyId);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.deleteComputerBunch", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
	}
	
	public int CountComputers(Page page) throws SQLException {
		int count = 0;
		try (Connection conn = database.getConnection();) {

			PreparedStatement stmt = conn.prepareStatement(String.format(COUNT_COMPUTERS_BY_SEARCH_REQUEST, page.getSearchBy().getColumn()));
			stmt.setString(1, page.getSearch());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			count = rs.getInt("count");
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		return count;
	}

}
