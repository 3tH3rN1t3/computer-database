package com.excilys.cdb.service;

import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.SearchBy;
import com.excilys.cdb.persistence.repository.ComputerRepository;

@Component
@Scope("singleton")
public class ComputerService {
	
	private ComputerRepository repository;
		
	private ComputerService(ComputerRepository repository) {
		this.repository = repository;
	}
	

	public Page<Computer> search(Pageable p, SearchBy column, String filter) {
		if (column == SearchBy.INTRODUCED) {
			return repository.findAllByIntroduced(p, filter);
		} else if (column == SearchBy.DISCONTINUED) {
			return repository.findAllByDiscontinued(p, filter);
		} else if (column == SearchBy.COMPANY) {
			return repository.findAllByCompany(p, filter);
		} else {
			return repository.findAllByName(p, filter);
		}
	}
	
	public Optional<Computer> getComputer(int id) {
		return repository.findById(id);
	}
	
	public void addComputer(Computer computer) {
		System.out.println(repository.save(computer));
	}
		
	public void updateComputer(Computer computer) {
		repository.save(computer);
	}
	
	public void deleteComputer(int id) {
		repository.deleteById(id);
	}
}
