package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.mapper.DBCompanyMapper;
import com.excilys.cdb.persistence.mapper.DBComputerMapper;

@Component
@Scope("singleton")
public class DBController {
	
	private ComputerDAO computerDAO;
	
	private CompanyDAO companyDAO;
	
	private DBComputerMapper computerMapper;
	

	private DBCompanyMapper companyMapper;
	
	
	private DBController(ComputerDAO computerDAO, CompanyDAO companyDAO, DBComputerMapper computerMapper, DBCompanyMapper companyMapper) {
		this.computerDAO = computerDAO;
		this.companyDAO = companyDAO;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
	}
	
	
	public ArrayList<Computer> search(Page page) {
		return this.computerMapper.toComputerArray(computerDAO.search(page));
	}
	
	public int countComputers(Page page) {
		return computerDAO.CountComputers(page);
	}
	
	
	public Optional<Computer> getComputer(int id) {
		return this.computerMapper.toComputer(computerDAO.find(id));
	}

	public void addComputer(Computer computer) {
		computerDAO.insertComputer(computerMapper.toComputerDTO(computer));
	}

	public int updateComputer(Computer computer) {
		return computerDAO.updateComputer(computerMapper.toComputerDTO(computer));
	}

	public int deleteComputer(int id) {
		return computerDAO.deleteComputer(id);
	}
	
	public ArrayList<Company> getAllCompanies() {
		return this.companyMapper.toCompanyArray(companyDAO.getAllCompanies());
	}
	
	public Optional<Company> getCompanyById(int id) {
		return this.companyMapper.toCompany(companyDAO.getCompanyById(id));
	}
	
	public int deleteCompany(int id) {
		return this.companyDAO.deleteCompany(id);
	}
}
