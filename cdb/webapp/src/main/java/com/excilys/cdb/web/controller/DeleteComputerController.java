package com.excilys.cdb.web.controller;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.service.ComputerService;

@Controller
public class DeleteComputerController {
	
	private ComputerService computerService;
	
    public DeleteComputerController(ComputerService computerService) {
    	this.computerService = computerService;
    }
    
    @PostMapping("/deleteComputer")
    public ModelAndView deleteComputer(ServletRequest request) {
    	String[] values = request.getParameterValues("cb");
    	for(String value : values) {
    		try {
    			int id = Integer.parseInt(value);
    			computerService.deleteComputer(id);
    		} catch (NumberFormatException e) {}
    	}
		return new ModelAndView("redirect:/dashboard");
    }

}
