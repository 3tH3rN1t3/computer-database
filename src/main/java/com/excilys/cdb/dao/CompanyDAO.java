package com.excilys.cdb.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.Database;

//singleton
public class CompanyDAO {
	
	private static Database db;
	private static CompanyDAO companyDAO;
	private static Logger logger;
	
	public static CompanyDAO getInstance() {
		if (companyDAO == null)
			companyDAO = new CompanyDAO();
		return companyDAO;
	}
	
	private CompanyDAO() {
		db = Database.getDB();
		logger = LoggerFactory.getLogger(getClass());
	}
	
	public ArrayList<Company> getSomeCompanies(int n, int offset) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Company> coms = new ArrayList<Company>();
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("SELECT id, name FROM company LIMIT ? OFFSET ?");
			stmt.setInt(1, n);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			coms = CompanyMapper.getMapper().map(rs);
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.getSomeCompanies", e);
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

	public Optional<Company> getCompanyById(int id) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Optional<Company> com;
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("SELECT id, name FROM company WHERE id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			com = CompanyMapper.getMapper().mapOne(rs);
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.getCompanyById", e);
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
			logger.info("Retreived company with ID " + id + " from the database\n");
		} else {
			logger.info("Failed to retreive company with ID " + id + " from the database\n");
		}
		return com;
	}
	
	public int countCompanies() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = db.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(id) AS elements FROM company");
			rs.next();
			count = rs.getInt("elements");
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.countCompanies", e);
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
