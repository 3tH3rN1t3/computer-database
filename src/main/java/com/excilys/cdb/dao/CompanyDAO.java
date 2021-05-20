package com.excilys.cdb.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.Database;

//singleton
public class CompanyDAO {
	
	private static Database db;
	private static CompanyDAO companyDAO;
	
	public static CompanyDAO getInstance() {
		if (companyDAO == null)
			companyDAO = new CompanyDAO();
		return companyDAO;
	}
	
	private CompanyDAO() {
		db = Database.getDB();
	}
	
	public ArrayList<Company> getAllCompanies() throws SQLException {
		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT id, name FROM company");
		ArrayList<Company> coms = CompanyMapper.getMapper().map(rs);
		rs.close();
		stmt.close();
		conn.close();
		return coms;
	}
	
	public ArrayList<Company> getSomeCompanies(int n, int offset) throws SQLException {
		Connection conn = db.getConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM company LIMIT ?,?");
		stmt.setInt(1, n);
		stmt.setInt(2, offset);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Company> coms = CompanyMapper.getMapper().map(rs);
		rs.close();
		stmt.close();
		conn.close();
		return coms;
	}

	public Optional<Company> getCompanyById(int id) throws SQLException{
		Connection conn = db.getConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM company WHERE id = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Company> coms = CompanyMapper.getMapper().map(rs);
		rs.close();
		stmt.close();
		conn.close();
		if (!coms.isEmpty())
			return Optional.of(coms.get(0));
		return Optional.empty();
	}
	
	public int CountCompanies() throws SQLException {
		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select count(id) as elements from company");
		int count = -1;
		if(rs.next()) {
			count = rs.getInt("elements");
		}
		return count;
	}
}
