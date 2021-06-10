package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.controller.DBController;
import com.excilys.cdb.model.Company;

@Component
@Scope("singleton")
public class CompanyService {
	
	@Autowired
	private DBController controller;
		
	private CompanyService() {
	}
	
	public ArrayList<Company> getAllCompanies() {
		return controller.getAllCompanies();
	}
	
	public Optional<Company> getCompanyById(int id) {
		return controller.getCompanyById(id);
	}
	
	public int deleteCompany(int id) {
		return controller.deleteCompany(id);
	}
}
