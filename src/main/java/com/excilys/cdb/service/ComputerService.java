package com.excilys.cdb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerService {
	
	private static ComputerService instance ;	
	private ComputerDAO dao;
	private ComputerMapper computerMapper;
		
		private ComputerService() throws IOException {
			this.dao = ComputerDAO.getInstance();
			this.computerMapper = ComputerMapper.getInstance();
		}

		public static ComputerService getInstance() throws IOException  {
			if (instance == null) {
				instance = new ComputerService();
			}
			return instance;
		}
		
		public ArrayList<Computer> getComputers(Pagination page) {
			return this.computerMapper.toComputers(dao.getComputersPerPage(page));
		}
		
		public int countComputers(){
			return dao.CountComputers();
		}
		
		
		public ArrayList<Computer>  search(String search, Pagination page) {
			
			return this.computerMapper.toComputers(dao.search(search, page));		
		}
		
		public int countComputers(String search){
			return dao.CountComputers(search);
		}
		
		
		public Computer getComputer(int id) {
			return this.computerMapper.toComputer(dao.getComputerById(id));	
		}

		public void addComputer(ComputerDTO computerDTO) {
			Computer computer = this.computerMapper.toComputer(Optional.of(computerDTO));
			dao.insertComputer(computer);
		}

		public int updateComputer(ComputerDTO computerDTO) {
			Computer computer = this.computerMapper.toComputer(Optional.of(computerDTO));
			System.out.println("Logg ComputerService update :"+ computer);

			if (computer != null) {
				return dao.updateComputer(computer);
			}else {
				System.out.println("Logg ComputerService update : Model Computer not valid");
			}
			return 0;
			
		}

		public void deletComputer(int id) {
			dao.deleteComputer(id);
		}
}
