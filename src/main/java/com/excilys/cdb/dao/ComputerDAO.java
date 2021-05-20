package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.Database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//singleton
//id | name | introduced | discontinued | company_id
public class ComputerDAO {
	
	private static Database db;
	private static ComputerDAO computerDAO;
	private Logger logger;
	
	public static ComputerDAO getInstance() {
		if (computerDAO == null)
			computerDAO = new ComputerDAO();
		return computerDAO;
	}
	
	private ComputerDAO() {
		db = Database.getDB();
		logger = LoggerFactory.getLogger(getClass());
	}
	
	public ArrayList<Computer> getSomeComputers(int n, int offset) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Computer> coms = new ArrayList<Computer>();
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued"
					+ ", company.id, company.name "
					+ "FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?");
			stmt.setInt(1, n);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			coms = ComputerMapper.getMapper().map(rs);
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { }
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { }
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { }
			}
		}
		logger.info("Retreived " + coms.size() + " lines from the database\n");
		return coms;
	}

	public Optional<Computer> getComputerById(int id) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Optional<Computer> com;
		try {
			conn = db.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id");
			com = ComputerMapper.getMapper().mapOne(rs);
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { }
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { }
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { }
			}
		}
		if (com.isPresent()) {
			logger.info("Retreived computer with ID " + id + " from the database\n");
		} else {
			logger.info("Failed to retreive computer with ID " + id + " from the database\n");
		}
		return com;
	}
	
	public int insertComputer(Computer com) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int id;
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("INSERT INTO computer (name, introduced, discontinued, company_id) "
				+ "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, com.getName());
			if (com.getAddDate().isPresent()) {
				stmt.setDate(2, Date.valueOf(com.getAddDate().get()));
			} else {
				stmt.setDate(2, null);
			}
			if (com.getRemoveDate().isPresent()) {
				stmt.setDate(3, Date.valueOf(com.getRemoveDate().get()));
			} else {
				stmt.setDate(3, null);
			}
			if (com.getCompany().isPresent()) {
				stmt.setInt(4, com.getCompany().get().getID());
			} else {
				stmt.setString(4, null);
			}
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.first();
			id = rs.getInt(1);
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { }
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { }
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { }
			}
		}
		logger.info("Inserted computer with ID " + id + " into the database\n");
		return id;
	}
	
	public int updateComputer(Computer com) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int edits;
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("UPDATE computer "
					+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
					+ "WHERE id = ?");
			stmt.setString(1, com.getName());
			if (com.getAddDate().isPresent()) {
				stmt.setDate(2, Date.valueOf(com.getAddDate().get()));
			} else {
				stmt.setDate(2, null);
			}
			if (com.getRemoveDate().isPresent()) {
				stmt.setDate(3, Date.valueOf(com.getRemoveDate().get()));
			} else {
				stmt.setDate(3, null);
			}
			if (com.getCompany().isPresent()) {
				stmt.setInt(4, com.getCompany().get().getID());
			} else {
				stmt.setString(4, null);
			}
			stmt.setInt(5, com.getID());
			edits = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { }
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { }
			}
		}
		logger.info("Updated computer with ID " + com.getID() + " in the database\n");
		return edits;
	}
	
	public int deleteComputer(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int deletes;
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("DELETE FROM computer WHERE id = ?");
			stmt.setInt(1, id);
			deletes = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { }
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { }
			}
		}
		logger.info("Deleted computer with ID " + id + " from the database\n");
		return deletes;
	}
	
	public int CountComputers() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count;
		try {
			conn = db.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(id) AS elements FROM computer");
			rs.next();
			count = rs.getInt("elements");
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { }
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) { }
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) { }
			}
		}
		return count;
	}

}
