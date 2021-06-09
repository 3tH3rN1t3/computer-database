package com.excilys.cdb.web.controller;

import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletRequest;

import org.apache.logging.log4j.LogManager;
import  org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.service.ComputerService;

@Controller
@Scope("request")
public class DeleteComputerController {
	
	@Autowired
	private ComputerService computerService;
	
	private static final Logger LOGGER = LogManager.getLogger(DeleteComputerController.class);
	
    public DeleteComputerController() {
    }
    
    @PostMapping("/deleteComputer")
    public void deleteComputer(ServletRequest request) {
    	String values = request.getParameter("selection");
    	Scanner scanner = new java.util.Scanner(values).useDelimiter(",");
		while (scanner.hasNextInt()) {
			int id = scanner.nextInt();
			try {
				computerService.deleteComputer(id);
			} catch (SQLException e) {
				LOGGER.error("Oups, erreur SQL");
			}
		}
		scanner.close();
    }

}
