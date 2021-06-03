package com.excilys.cdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.mapper.DBCompanyMapper;
import com.excilys.cdb.mapper.DBComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;

public class DBController {
	
	private static DBController instance;
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	private DBComputerMapper computerMapper;
	private DBCompanyMapper companyMapper;
	
	private DBController() throws IOException {
		this.computerDAO = ComputerDAO.getInstance();
		this.companyDAO = CompanyDAO.getInstance();
		this.computerMapper = DBComputerMapper.getInstance();
		this.companyMapper = DBCompanyMapper.getInstance();
	}
	
	public static DBController getInstance() throws IOException {
		if (instance == null) {
			instance = new DBController();
		}
		return instance;
	}
	
	
	public ArrayList<Computer> search(Page page) throws SQLException {
		return this.computerMapper.toComputers(computerDAO.search(page));
	}
	
	public int countComputers(Page page) throws SQLException{
		return computerDAO.CountComputers(page);
	}
	
	
	public Optional<Computer> getComputer(int id) throws SQLException {
		return this.computerMapper.toComputer(computerDAO.find(id));
	}

	public void addComputer(Computer computer) throws SQLException {
		computerDAO.insertComputer(computerMapper.toComputerDTO(computer));
	}

	public int updateComputer(Computer computer) throws SQLException {
		return computerDAO.updateComputer(computerMapper.toComputerDTO(computer));
	}

	public void deleteComputer(int id) throws SQLException {
		computerDAO.deleteComputer(id);
	}
	
	public ArrayList<Company> getAllCompanies() throws SQLException {
		return this.companyMapper.toCompanies(companyDAO.getAllCompanies());
	}
	
	public ArrayList<Company> getCompaniesPerPage(Page p) throws SQLException {
		return this.companyMapper.toCompanies(companyDAO.getCompaniesPerPage(p));
	}
	
	public Optional<Company> getCompanyById(int id) throws SQLException {
		return this.companyMapper.toCompany(companyDAO.getCompanyById(id));
	}
	
	public Optional<Company> getCompanyByName(String name) throws SQLException {
		return this.companyMapper.toCompany(companyDAO.getCompanyByName(name));
	}
	
	public int deleteCompany(int id) throws SQLException, IOException {
		return this.companyDAO.deleteCompany(id);
	}
	
	public int countCompanies() throws SQLException {
		return companyDAO.countCompanies();
	}
}
