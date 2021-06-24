package com.excilys.cdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.SearchBy;
import com.excilys.cdb.persistence.dto.DBComputerDTO;
import com.excilys.cdb.persistence.mapper.DBCompanyMapper;
import com.excilys.cdb.persistence.mapper.DBComputerMapper;
import com.excilys.cdb.persistence.repository.CompanyDAO;
import com.excilys.cdb.persistence.repository.CompanyRepository;
import com.excilys.cdb.persistence.repository.ComputerRepository;

@Component
public class DBController {
	
	private ComputerRepository computerRepository;
	
	private CompanyRepository companyRepository;
	
	private CompanyDAO companyDAO;
	
	private DBComputerMapper computerMapper;
	
	private DBCompanyMapper companyMapper;
	
	private DBController(ComputerRepository computerRepository, CompanyRepository companyRepository, CompanyDAO companyDAO, DBComputerMapper computerMapper, DBCompanyMapper companyMapper) {
		this.computerRepository = computerRepository;
		this.companyRepository = companyRepository;
		this.companyDAO = companyDAO;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
	}
	

	public Page<Computer> search(Pageable p, SearchBy column, String filter) {
		Page<DBComputerDTO> page;
		if (column == SearchBy.INTRODUCED) {
			page = computerRepository.findByIntroduced(p, filter);
		} else if (column == SearchBy.DISCONTINUED) {
			page = computerRepository.findByDiscontinued(p, filter);
		} else if (column == SearchBy.COMPANY) {
			page = computerRepository.findByCompanyName(p, filter);
		} else {
			page = computerRepository.findByName(p, filter);
		}
		
		return page.map(dto -> computerMapper.toComputer(Optional.of(dto)).get());
	}
	
	public Optional<Computer> getComputer(int id) {
		return computerMapper.toComputer(computerRepository.findById(id));
	}
	
	public Computer addComputer(Computer computer) {
		return computerMapper.toComputer(computerRepository.save(computerMapper.toComputerDTO(computer)));
	}
		
	public Computer updateComputer(Computer computer) {
		return computerMapper.toComputer(computerRepository.save(computerMapper.toComputerDTO(computer)));
	}
	
	public void deleteComputer(int id) {
		computerRepository.deleteById(id);
	}
	
	public int count(SearchBy column, String filter) {
		if (column == SearchBy.INTRODUCED) {
			return computerRepository.countByIntroducedContaining(filter);
		} else if (column == SearchBy.DISCONTINUED) {
			return computerRepository.countByDiscontinuedContaining(filter);
		} else if (column == SearchBy.COMPANY) {
			return computerRepository.countByCompanyNameContaining(filter);
		} else {
			return computerRepository.countByNameContaining(filter);
		}
	}
	
	public List<Company> getAllCompanies() {
		return companyMapper.toCompanyArray(companyRepository.findAll());
	}
	
	public Optional<Company> getCompanyById(int id) {
		return companyMapper.toCompany(companyRepository.findById(id));
	}
	
	public int deleteCompany(int id) {
		return companyDAO.deleteById(id);
	}
	
	public int countCompanies() {
		return (int) companyRepository.count();
	}
	
}
