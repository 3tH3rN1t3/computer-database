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

import com.excilys.cdb.dto.DBCompanyDTO;
import com.excilys.cdb.exceptions.AmbiguousNameException;
import com.excilys.cdb.mapper.DBCompanyMapper;
import com.excilys.cdb.model.Page;

public class CompanyDAO {
	
	private static final String GET_ALL_COMPANIES_REQUEST = "SELECT company.id, company.name FROM company";
	
	private static final String GET_COMPANIES_PER_PAGE_REQUEST = "SELECT company.id, company.name FROM company LIMIT ? OFFSET ?";
	
	private static final String GET_COMPANY_BY_ID = "SELECT company.id, company.name FROM company WHERE id = ?";
	
	private static final String GET_COMPANY_BY_NAME = "SELECT company.id, company.name FROM company WHERE name LIKE CONCAT('%',?,'%')";
	
	private static final String COUNT_COMPANIES_REQUEST = "SELECT COUNT(id) AS count FROM company";
	
	private static final String DELETE_COMPANY_REQUEST = "DELETE FROM company WHERE id = ?";
	
	private static CompanyDAO companyDAO;
	
	private Database db;
	private DBCompanyMapper mapper;
	private Logger logger;
	
	
	public static CompanyDAO getInstance() throws IOException {
		if (companyDAO == null)
			companyDAO = new CompanyDAO();
		return companyDAO;
	}
	
	private CompanyDAO() throws IOException {
		db = Database.getInstance();
		mapper = DBCompanyMapper.getInstance();
		logger = LogManager.getLogger(getClass());
	}
	
	//TODO passer la connection en argument?
	
	public ArrayList<DBCompanyDTO> getAllCompanies() throws SQLException {
		ArrayList<DBCompanyDTO> coms = new ArrayList<DBCompanyDTO>();
		try (Connection conn = db.getConnection();) {
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(GET_ALL_COMPANIES_REQUEST);
			coms = mapper.toCompanyDTOs(rs);
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.getSomeCompanies", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Retreived " + coms.size() + " lines from the database\n");
		return coms;
	}
	
	public ArrayList<DBCompanyDTO> getCompaniesPerPage(Page p) throws SQLException {
		ArrayList<DBCompanyDTO> coms = new ArrayList<DBCompanyDTO>();
		try (Connection conn = db.getConnection();) {

			PreparedStatement stmt = conn.prepareStatement(GET_COMPANIES_PER_PAGE_REQUEST);
			stmt.setInt(1, p.getMaxItems());
			stmt.setInt(2, (p.getNumPage()-1)*p.getMaxItems());
			ResultSet rs = stmt.executeQuery();
			coms = DBCompanyMapper.getInstance().toCompanyDTOs(rs);
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.getSomeCompanies", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		logger.info("Retreived " + coms.size() + " lines from the database\n");
		return coms;
	}

	public Optional<DBCompanyDTO> getCompanyById(int id) throws SQLException{
		Optional<DBCompanyDTO> com;
		try (Connection conn = db.getConnection();){

			PreparedStatement stmt = conn.prepareStatement(GET_COMPANY_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			com = mapper.toCompanyDTO(rs);
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

	public Optional<DBCompanyDTO> getCompanyByName(String name) throws SQLException{
		Optional<DBCompanyDTO> com;
		try (Connection conn = db.getConnection();){

			PreparedStatement stmt = conn.prepareStatement(GET_COMPANY_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			ArrayList<DBCompanyDTO> coms = mapper.toCompanyDTOs(rs);
			switch(coms.size()) {
				case 0:
					com = Optional.empty();
					break;
				case 1:
					com = Optional.of(coms.get(0));
					break;
				default:
					throw new AmbiguousNameException("Retreived multiple companies with names like '" + name + "' from the database\n");
			}
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.getCompanyByName", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		if (com.isPresent()) {
			logger.info("Retreived company with name " + com.get().getName() + " from the database\n");
		} else {
			logger.info("Failed to retreive company with name like '" + name + "' from the database\n");
		}
		return com;
	}
	
	public int countCompanies() throws SQLException {
		int count = 0;
		try (Connection conn = db.getConnection();){

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(COUNT_COMPANIES_REQUEST);
			rs.next();
			count = rs.getInt("count");
		} catch (SQLException e) {
			logger.error("Error in CompanyDAO.countCompanies", e);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		return count;
	}
	
	public synchronized int deleteCompany(int id) throws SQLException, IOException {
		int deletionCount = 0;
		try (Connection conn = db.getConnection();) {
			try {
				conn.setAutoCommit(false);
				deletionCount = ComputerDAO.getInstance().deleteComputerBunch(id, conn);
				PreparedStatement stmt = conn.prepareStatement(DELETE_COMPANY_REQUEST);
				stmt.setInt(1, id);
				deletionCount += stmt.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw new SQLException();
			}
		} catch (SQLException ex) {
			logger.error("Error in CompanyDAO.deleteCompany", ex);
			throw new SQLException("Une erreur est survenue lors de l'exécution de votre requête");
		}
		return deletionCount;
	}
}
