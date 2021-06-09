package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.mapper.DBCompanyMapper;
import com.excilys.cdb.mapper.DBComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;

@Component
@Scope("singleton")
public class DBController {
	
	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private CompanyDAO companyDAO;
	
	@Autowired
	private DBComputerMapper computerMapper;
	
	@Autowired
	private DBCompanyMapper companyMapper;
	
	
	private DBController() {
	}
	
	
	public ArrayList<Computer> search(Page page) throws SQLException {
		return this.computerMapper.toComputerArray(computerDAO.search(page));
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

	public int deleteComputer(int id) throws SQLException {
		return computerDAO.deleteComputer(id);
	}
	
	public ArrayList<Company> getAllCompanies() throws SQLException {
		return this.companyMapper.toCompanyArray(companyDAO.getAllCompanies());
	}
	
	public Optional<Company> getCompanyById(int id) throws SQLException {
		return this.companyMapper.toCompany(companyDAO.getCompanyById(id));
	}
	
	public int deleteCompany(int id) throws SQLException {
		return this.companyDAO.deleteCompany(id);
	}
}
