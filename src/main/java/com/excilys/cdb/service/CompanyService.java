package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.controller.DBController;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

@Component
@Scope("singleton")
public class CompanyService {
	
	@Autowired
	private DBController controller;
		
	private CompanyService() {
	}
	
	public ArrayList<Company> getAllCompanies() throws SQLException {
		return controller.getAllCompanies();
	}
	
	public ArrayList<Company> getCompaniesPerPage(Page p) throws SQLException {
		return controller.getCompaniesPerPage(p);
	}
	
	public Optional<Company> getCompanyById(int id) throws SQLException {
		return controller.getCompanyById(id);
	}
	
	public Optional<Company> getCompanyByName(String name) throws SQLException {
		return controller.getCompanyByName(name);
	}
	
	public int deleteCompany(int id) throws SQLException {
		return controller.deleteCompany(id);
	}
	
	public int countCompanies() throws SQLException {
		return controller.countCompanies();
	}
}
