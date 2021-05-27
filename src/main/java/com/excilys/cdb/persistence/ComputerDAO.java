package com.excilys.cdb.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Pagination;


//singleton
//id | name | introduced | discontinued | company_id
public class ComputerDAO {
	
	private static final String GET_COMPUTERS_BY_PAGE_REQUEST = "SELECT computer.id, computer.name, "
			+ "introduced, discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?";
	
	private static final String GET_COMPUTER_BY_ID_REQUEST = "SELECT computer.id, computer.name, "
			+ "introduced, discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE computer.id = ?";
	
	private static final String GET_COMPUTERS_BY_SEARCH_REQUEST = "SELECT computer.id, computer.name, "
			+ "introduced, discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE computer.name LIKE CONCAT('%', ?, '%') LIMIT ? OFFSET ?";
	
	private static final String INSERT_COMPUTER_REQUEST = "INSERT INTO computer "
			+ "(name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	
	private static final String UPDATE_COMPUTER_REQUEST = "UPDATE computer "
			+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
			+ "WHERE id = ?";
	
	private static final String DELETE_COMPUTER_REQUEST = "DELETE FROM computer WHERE id = ?";
	
	private static final String COUNT_COMPUTERS_REQUEST = "SELECT COUNT(id) AS count FROM computer";
	
	private static final String COUNT_COMPUTERS_BY_SEARCH_REQUEST = "SELECT COUNT(id) AS count FROM computer "
			+ "WHERE name LIKE CONCAT('%', ?, '%')";
	
	private static Database db;
	
	private static ComputerDAO computerDAO;
	
	private static Logger logger = LogManager.getLogger(ComputerDAO.class);
	
	
	private ComputerDAO() throws IOException {
		db = Database.getInstance();
	}
	
	public static ComputerDAO getInstance() throws IOException {
		if (computerDAO == null)
			computerDAO = new ComputerDAO();
		return computerDAO;
	}
	
	
	public ArrayList<Computer> getComputersPerPage(Pagination p) throws SQLException {
		ArrayList<Computer> coms = new ArrayList<Computer>();
		try (Connection conn = db.getConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_COMPUTERS_BY_PAGE_REQUEST);) {
			
			stmt.setInt(1, p.getMaxItems());
			stmt.setInt(2, (p.getNumPage()-1)*p.getMaxItems());
			ResultSet rs = stmt.executeQuery();
			coms = ComputerMapper.getInstance().map(rs);
			
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Retreived " + coms.size() + " lines from the database\n");
		return coms;
	}
	
	public ArrayList<Computer> getComputersBySearch(String pattern, Pagination p) throws SQLException {
		ArrayList<Computer> coms = new ArrayList<Computer>();
		try (Connection conn = db.getConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_COMPUTERS_BY_SEARCH_REQUEST);) {
			
			stmt.setString(1, pattern);
			stmt.setInt(2, p.getMaxItems());
			stmt.setInt(3, (p.getNumPage()-1)*p.getMaxItems());
			ResultSet rs = stmt.executeQuery();
			coms = ComputerMapper.getInstance().map(rs);
			
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Retreived " + coms.size() + " lines from the database\n");
		return coms;
	}

	public Optional<Computer> getComputerById(int id) throws SQLException{
		Optional<Computer> com;
		try (Connection conn = db.getConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_COMPUTER_BY_ID_REQUEST);) {
			
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			com = ComputerMapper.getInstance().mapOne(rs);
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		if (com.isPresent()) {
			logger.info("Retreived computer with ID " + id + " from the database\n");
		} else {
			logger.info("Failed to retreive computer with ID " + id + " from the database\n");
		}
		return com;
	}
	
	public int insertComputer(Computer com) throws SQLException {
		int id;
		try (Connection conn = db.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(INSERT_COMPUTER_REQUEST, Statement.RETURN_GENERATED_KEYS);) {
			
			stmt.setString(1, com.getName());
			//J'adore ce bout de code
			stmt.setDate(2, com.getIntroduced().map(Date::valueOf).orElse(null));
			stmt.setDate(3, com.getDiscontinued().map(Date::valueOf).orElse(null));
			stmt.setString(4, com.getCompany().map(Company::getID).map(String::valueOf).orElse(null));
			//C'est fini
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.first();
			id = rs.getInt(1);
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Inserted computer with ID " + id + " into the database\n");
		return id;
	}
	
	public int updateComputer(Computer com) throws SQLException {
		int edits;
		try (Connection conn = db.getConnection();
				PreparedStatement stmt = conn.prepareStatement(UPDATE_COMPUTER_REQUEST);) {
			
			stmt.setString(1, com.getName());
			stmt.setDate(2, com.getIntroduced().map(Date::valueOf).orElse(null));
			stmt.setDate(3, com.getDiscontinued().map(Date::valueOf).orElse(null));
			stmt.setString(4, com.getCompany().map(Company::getID).map(String::valueOf).orElse(null));
			stmt.setInt(5, com.getID());
			edits = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Updated computer with ID " + com.getID() + " in the database\n");
		return edits;
	}
	
	public int deleteComputer(int id) throws SQLException {
		int deletes;
		try (Connection conn = db.getConnection();
				PreparedStatement stmt = conn.prepareStatement(DELETE_COMPUTER_REQUEST);) {
			
			stmt.setInt(1, id);
			deletes = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Deleted computer with ID " + id + " from the database\n");
		return deletes;
	}
	
	public int CountComputers() throws SQLException {
		int count;
		try (Connection conn = db.getConnection();
				Statement stmt = conn.createStatement();) {
			
			ResultSet rs = stmt.executeQuery(COUNT_COMPUTERS_REQUEST);
			rs.next();
			count = rs.getInt("count");
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		return count;
	}
	
	public int CountComputers(String search) throws SQLException {
		int count;
		try (Connection conn = db.getConnection();
				PreparedStatement stmt = conn.prepareStatement(COUNT_COMPUTERS_BY_SEARCH_REQUEST);) {
			
			stmt.setString(1, search);
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
