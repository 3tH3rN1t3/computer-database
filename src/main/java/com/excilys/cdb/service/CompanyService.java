package com.excilys.cdb.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.controller.DBController;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public class CompanyService {
	
	private static CompanyService instance ;
	
	private DBController controller;
		
	private CompanyService() throws IOException {
		controller = DBController.getInstance();
	}
	
	public static CompanyService getInstance() throws IOException  {
		if (instance == null) {
			instance = new CompanyService();
		}
		return instance;
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
	
	public int deleteCompany(int id) throws SQLException, IOException {
		return controller.deleteCompany(id);
	}
	
	public int countCompanies() throws SQLException {
		return controller.countCompanies();
	}
}
