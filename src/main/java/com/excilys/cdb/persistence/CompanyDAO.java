package com.excilys.cdb.persistence;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Pagination;

//singleton
public class CompanyDAO {
	
	private static final String GET_COMPANIES_PER_PAGE_REQUEST = "SELECT id, name FROM company LIMIT ? OFFSET ?";
	
	private static final String GET_COMPANY_BY_ID = "SELECT id, name FROM company WHERE id = ?";
	
	private static final String COUNT_COMPANIES_REQUEST = "SELECT COUNT(id) AS count FROM company";
	
	private static Database db;
	
	private static CompanyDAO companyDAO;
	
	private static Logger logger;
	
	
	public static CompanyDAO getInstance() throws IOException {
		if (companyDAO == null)
			companyDAO = new CompanyDAO();
		return companyDAO;
	}
	
	private CompanyDAO() throws IOException {
		db = Database.getInstance();
		logger = LogManager.getLogger(getClass());
	}
	
	public ArrayList<Company> getCompaniesPerPage(Pagination p) throws SQLException {
		ArrayList<Company> coms = new ArrayList<Company>();
		try (Connection conn = db.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(GET_COMPANIES_PER_PAGE_REQUEST);) {
			
			stmt.setInt(1, p.getMaxItems());
			stmt.setInt(2, (p.getNumPage()-1)*p.getMaxItems());
			ResultSet rs = stmt.executeQuery();
			coms = CompanyMapper.getMapper().map(rs);
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.getSomeCompanies", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Retreived " + coms.size() + " lines from the database\n");
		return coms;
	}

	public Optional<Company> getCompanyById(int id) throws SQLException{
		Optional<Company> com;
		try (Connection conn = db.getConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_COMPANY_BY_ID);){
			
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			com = CompanyMapper.getMapper().mapOne(rs);
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.getCompanyById", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		if (com.isPresent()) {
			logger.info("Retreived company with ID " + id + " from the database\n");
		} else {
			logger.info("Failed to retreive company with ID " + id + " from the database\n");
		}
		return com;
	}
	
	public int countCompanies() throws SQLException {
		int count = 0;
		try (Connection conn = db.getConnection();
				Statement stmt = conn.createStatement();){
			
			ResultSet rs = stmt.executeQuery(COUNT_COMPANIES_REQUEST);
			rs.next();
			count = rs.getInt("count");
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.countCompanies", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		return count;
	}
}
