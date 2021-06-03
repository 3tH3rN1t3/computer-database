package com.excilys.cdb.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.controller.DBController;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class ComputerService {
	
	private static ComputerService instance ;
	
	private DBController controller;
		
		private ComputerService() throws IOException {
			controller = DBController.getInstance();
		}

		public static ComputerService getInstance() throws IOException  {
			if (instance == null) {
				instance = new ComputerService();
			}
			return instance;
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

		public void deleteComputer(int id) throws SQLException {
			controller.deleteComputer(id);
		}
}
