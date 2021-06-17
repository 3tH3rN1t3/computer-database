package com.excilys.cdb.web.controller;

import java.util.Scanner;

import javax.servlet.ServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.cdb.service.ComputerService;

@Controller
@Scope("request")
public class DeleteComputerController {
	
	private ComputerService computerService;
	
    public DeleteComputerController(ComputerService computerService) {
    	this.computerService = computerService;
    }
    
    @PostMapping("/deleteComputer")
    public void deleteComputer(ServletRequest request) {
    	String values = request.getParameter("selection");
    	Scanner scanner = new java.util.Scanner(values).useDelimiter(",");
		while (scanner.hasNextInt()) {
			int id = scanner.nextInt();
			computerService.deleteComputer(id);
		}
		scanner.close();
    }

}
