package com.excilys.cdb.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.controller.DBController;
import com.excilys.cdb.dto.DBComputerDTO;
import com.excilys.cdb.mapper.DBComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerService {
	
	private static ComputerService instance ;
	
	private static DBController controller;
		
		private ComputerService() throws IOException {
			controller = DBController.getInstance();
		}

		public static ComputerService getInstance() throws IOException  {
			if (instance == null) {
				instance = new ComputerService();
			}
			return instance;
		}
		
		
		public ArrayList<Computer>  search(String search, Pagination page) throws SQLException {
			
			return controller.search(search, page);		
		}
		
		public int countComputers(String search) throws SQLException{
			return controller.countComputers(search);
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

		public void deleteComputer(int id) throws SQLException {
			controller.deleteComputer(id);
		}
}
