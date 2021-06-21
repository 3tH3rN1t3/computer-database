package com.excilys.cdb.service;

import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.excilys.cdb.controller.DBController;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.SearchBy;

@Component
@Scope("singleton")
public class ComputerService {
	
	private DBController controller;
		
	private ComputerService(DBController controller) {
		this.controller = controller;
	}
	

	public Page<Computer> search(Pageable p, SearchBy column, String filter) {
		return controller.search(p, column, filter);
	}
	
	public Optional<Computer> getComputer(int id) {
		return controller.getComputer(id);
	}
	
	public int addComputer(Computer computer) {
		return controller.addComputer(computer);
	}
		
	public void updateComputer(Computer computer) {
		controller.updateComputer(computer);
	}
	
	public void deleteComputer(int id) {
		controller.deleteComputer(id);
	}
}
