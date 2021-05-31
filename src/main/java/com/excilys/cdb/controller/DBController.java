package com.excilys.cdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.dto.DBComputerDTO;
import com.excilys.cdb.mapper.DBComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.persistence.ComputerDAO;

public class DBController {
	
	private static DBController instance;
	private ComputerDAO dao;
	private DBComputerMapper computerMapper;
	
	private DBController() throws IOException {
		this.dao = ComputerDAO.getInstance();
		this.computerMapper = DBComputerMapper.getInstance();
	}
	
	public static DBController getInstance() throws IOException {
		if (instance == null) {
			instance = new DBController();
		}
		return instance;
	}
	
	public ArrayList<Computer> getComputers(Pagination page) throws SQLException {
		return this.computerMapper.toComputers(dao.getComputersPerPage(page));
	}
	
	public int countComputers() throws SQLException{
		return dao.CountComputers();
	}
	
	
	public ArrayList<Computer>  search(String search, Pagination page) throws SQLException {
		
		return this.computerMapper.toComputers(dao.search(search, page));		
	}
	
	public int countComputers(String search) throws SQLException{
		return dao.CountComputers(search);
	}
	
	
	public Computer getComputer(int id) throws SQLException {
		return this.computerMapper.toComputer(dao.find(id)).orElse(null);
	}

	public void addComputer(Computer computer) throws SQLException {
		dao.insertComputer(computerMapper.toComputerDTO(computer));
	}

	public int updateComputer(Computer computer) throws SQLException {
		return dao.updateComputer(computerMapper.toComputerDTO(computer));
	}

	public void deleteComputer(int id) throws SQLException {
		dao.deleteComputer(id);
	}
}
