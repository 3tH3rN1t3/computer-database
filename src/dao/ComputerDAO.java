package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import mapper.ComputerMapper;
import model.Computer;
import persistence.Database;

//singleton
//id | name | introduced | discontinued | company_id
public class ComputerDAO {
	
	private static Database db;
	private static ComputerDAO computerDAO;
	
	public static ComputerDAO getInstance() {
		if (computerDAO == null)
			computerDAO = new ComputerDAO();
		return computerDAO;
	}
	
	private ComputerDAO() {
		db = Database.getDB();
	}
	
	public ArrayList<Computer> getAllComputers() throws SQLException {
		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id");
		ArrayList<Computer> coms = ComputerMapper.getMapper().map(rs);
		rs.close();
		stmt.close();
		conn.close();
		return coms;
	}
	
	public ArrayList<Computer> getSomeComputers(int n, int offset) throws SQLException {
		Connection conn = db.getConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?");
		stmt.setInt(1, n);
		stmt.setInt(2, offset);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Computer> coms = ComputerMapper.getMapper().map(rs);
		rs.close();
		stmt.close();
		conn.close();
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
