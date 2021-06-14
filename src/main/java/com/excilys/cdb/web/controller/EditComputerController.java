package com.excilys.cdb.web.controller;

import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.web.dto.Locale;
import com.excilys.cdb.web.dto.WebComputerDTO;
import com.excilys.cdb.web.mapper.WebCompanyMapper;
import com.excilys.cdb.web.mapper.WebComputerMapper;
import com.excilys.cdb.web.validator.ComputerValidator;

@Controller
@Scope("request")
public class EditComputerController {
	
	private ComputerService computerService;
	
	private CompanyService companyService;

	private WebComputerMapper computerMapper;

	private WebCompanyMapper companyMapper;
	
	private ComputerValidator validator;
	
	private LocaleResolver localeResolver;
	
    public EditComputerController(ComputerService computerService, CompanyService companyService, WebComputerMapper computerMapper, WebCompanyMapper companyMapper, ComputerValidator validator, LocaleResolver localeResolver) {
    	this.computerService = computerService;
    	this.companyService = companyService;
    	this.computerMapper = computerMapper;
    	this.companyMapper = companyMapper;
    	this.validator = validator;
    	this.localeResolver = localeResolver;
    }
    
    @GetMapping("/editComputer")
    @ResponseBody
    public ModelAndView getEditComputer(ServletRequest request) {
    	
    	Computer computer = getComputer(request.getParameter("id"));
		ArrayList<Company> companies = companyService.getAllCompanies();
		
		ModelAndView response = new ModelAndView("editComputer");
		
		response.addObject("companies", companyMapper.toCompanyDTOArray(companies));
		if (computer.getId() == 0) {
			response.addObject("operation", "add");
		} else {
			response.addObject("operation", "edit");
		}
		response.addObject("computer", computerMapper.toComputerDTO(computer));
		response.addObject("languages", Locale.values());
		response.addObject("lang", getLocale(request));
		
		return response;
    }
    
    @PostMapping("/editComputer")
    public ModelAndView editComputer(ServletRequest request, @Valid @ModelAttribute("computer")WebComputerDTO dto, BindingResult result) {
    	
    	validator.validate(dto, result);
    	
		if (result.hasErrors()) {
		
		//Champs mal renseignés
			
			ArrayList<Company> companies = companyService.getAllCompanies();
			
			ModelAndView response = new ModelAndView("editComputer");
			
			if (dto.getId().equals("0")) {
				response.addObject("operation", "add");
			} else {
				response.addObject("operation", "edit");
			}
			response.addObject("companies", companies);
			response.addObject("computer", dto);
			response.addObject("errors", result);
			response.addObject("languages", Locale.values());
			response.addObject("lang", getLocale(request));
			return response;
		} else {
	    	Computer computer = computerMapper.toComputer(dto);
			if (computer.getId() == 0) {
				computerService.addComputer(computer);
			} else {
				computerService.updateComputer(computer);
			}
		}
    	
		return new ModelAndView("redirect:/dashboard");
    }
    
	
    private Computer getComputer(String string) {
		try {
			int id = Integer.parseInt(string);
			return computerService.getComputer(id).orElse(null);
		} catch (NumberFormatException e) {
			return new Computer.ComputerBuilder(0, null).build();
		}
	}
	
	private Locale getLocale(ServletRequest request) {
		try {
			return Locale.valueOf(localeResolver.resolveLocale((HttpServletRequest) request).toString().toUpperCase());
		} catch (IllegalArgumentException e) {
			return Locale.FR;
		}
	}

}
