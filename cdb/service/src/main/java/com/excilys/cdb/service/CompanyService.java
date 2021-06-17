package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.repository.CompanyRepository;

@Component
@Scope("singleton")
public class CompanyService {
	
	private CompanyRepository repository;
		
	private CompanyService(CompanyRepository repository) {
		this.repository = repository;
	}
	
	public List<Company> getAllCompanies() {
		return repository.findAll();
	}
	
	public Optional<Company> getCompanyById(int id) {
		return repository.findById(id);
	}
	
	public void deleteCompany(int id) {
		repository.deleteById(id);
	}
}
