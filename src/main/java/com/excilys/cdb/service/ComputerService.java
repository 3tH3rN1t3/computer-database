package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.controller.DBController;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Component
@Scope("singleton")
public class ComputerService {
	
	@Autowired
	private DBController controller;
		
	private ComputerService() {
	}
	
	public ArrayList<Computer>  search(Page page) throws SQLException {
		return controller.search(page);		
	}
	
	public int countComputers(Page p) throws SQLException{
		return controller.countComputers(p);
	}
	
	public Optional<Computer> getComputer(int id) throws SQLException {
		return controller.getComputer(id);	
	}
	
	public void addComputer(Computer computer) throws SQLException {
		controller.addComputer(computer);
	}
		
	public int updateComputer(Computer computer) throws SQLException {
		return controller.updateComputer(computer);
	}
	
	public int deleteComputer(int id) throws SQLException {
		return controller.deleteComputer(id);
	}
}
