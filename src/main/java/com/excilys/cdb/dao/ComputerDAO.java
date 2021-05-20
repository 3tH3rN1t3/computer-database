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
		this.logger = LoggerFactory.getLogger(getClass());
	}
	
	public ArrayList<Computer> getSomeComputers(int n, int offset) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Computer> coms = new ArrayList<Computer>();
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?");
			stmt.setInt(1, n);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			coms = ComputerMapper.getMapper().map(rs);
		} catch (SQLException e) {
			logger.error("Error in ComputerDAO.getSomeComputers", e);
			throw new SQLException("désolé");
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
		logger.info("Retreived " + coms.size() + "lines from the database\n");
		return coms;
	}

	public Optional<Computer> getComputerById(int id) throws SQLException{
		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id");
		Optional<Computer> com = ComputerMapper.getMapper().mapOne(rs);
		rs.close();
		stmt.close();
		conn.close();
		return com;
	}
	
	public int insertComputer(Computer com) throws SQLException {
		Connection conn = db.getConnection();
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO computer (name, introduced, discontinued, company_id) "
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
		ResultSet rs = stmt.getGeneratedKeys();
		rs.first();
		int id = rs.getInt(1);
		rs.close();
		stmt.close();
		conn.close();
		return id;
	}
	
	public int updateComputer(Computer com) throws SQLException {
		Connection conn = db.getConnection();
		PreparedStatement stmt = conn.prepareStatement("UPDATE computer "
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
		int edits = stmt.executeUpdate();
		stmt.close();
		conn.close();
		return edits;
	}
	
	public int deleteComputer(int id) throws SQLException {
		Connection conn = db.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM computer WHERE id = ?");
		stmt.setInt(1, id);
		int deletes = stmt.executeUpdate();
		stmt.close();
		conn.close();
		return deletes;
	}
	
	public int CountComputers() throws SQLException {
		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select count(id) as elements from computer");
		int count = -1;
		if(rs.next()) {
			count = rs.getInt("elements");
		}
		return count;
	}

}
