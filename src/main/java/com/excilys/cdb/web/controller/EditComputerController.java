package com.excilys.cdb.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletRequest;

import org.apache.logging.log4j.LogManager;
import  org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.WebCompanyDTO;
import com.excilys.cdb.dto.WebComputerDTO;
import com.excilys.cdb.dto.WrongInputDTO;
import com.excilys.cdb.exceptions.CompanyIdNotValidException;
import com.excilys.cdb.exceptions.DateIntervalNotValidException;
import com.excilys.cdb.exceptions.DiscontinuedNotValidException;
import com.excilys.cdb.exceptions.IdNotValidException;
import com.excilys.cdb.exceptions.IntroducedNotValidException;
import com.excilys.cdb.exceptions.NameNotValidException;
import com.excilys.cdb.exceptions.ValidatorException;
import com.excilys.cdb.mapper.WebCompanyMapper;
import com.excilys.cdb.mapper.WebComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@Scope("request")
public class EditComputerController {
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;

	@Autowired
	private WebComputerMapper computerMapper;

	@Autowired
	private WebCompanyMapper companyMapper;
	
	private static final Logger LOGGER = LogManager.getLogger(EditComputerController.class);
	
    public EditComputerController() {
    }
    
    @GetMapping("/editComputer")
    @ResponseBody
    public ModelAndView getEditComputer(ServletRequest request) {
    	
    	Computer computer = null;
		ArrayList<Company> companies = new ArrayList<Company>();
		try {
			computer = getComputer(request.getParameter("id"));
			companies = companyService.getAllCompanies();
		} catch (SQLException e) {
			LOGGER.error("Oups erreur SQL");
		}
		
		ModelAndView response = new ModelAndView("editComputer");
		
		response.addObject("companies", companyMapper.toCompanyDTOArray(companies));
		if (computer == null) {
			response.addObject("operation", "Add");
		} else {
			response.addObject("operation", "Edit");
			response.addObject("computer", computerMapper.toComputerDTO(computer));
		}
		
		return response;
    }
    
    @PostMapping("/editComputer")
    public ModelAndView editComputer(ServletRequest request) {
    	try {
			WebComputerDTO dto = new WebComputerDTO.ComputerDTOBuilder(request.getParameter("id"), request.getParameter("computerName"))
					.withIntroduced(request.getParameter("introduced") == "" ? null : request.getParameter("introduced"))
					.withDiscontinued(request.getParameter("discontinued") == "" ? null : request.getParameter("discontinued"))
					.withCompany(request.getParameter("companyId") == "" ? null : new WebCompanyDTO(request.getParameter("companyId"), "company"))
					.build();
			try {
				Computer computer = computerMapper.toComputer(dto);
				if (computer.getId() == 0) {
					computerService.addComputer(computer);
				} else {
					computerService.updateComputer(computer);
				}
				
			} catch (ValidatorException e) {
				
				//Champs mal renseignés
				
				Throwable[] errors = e.getSuppressed();
				WrongInputDTO fails = new WrongInputDTO();
				for(Throwable error : errors) {
					if (error.getClass() == IdNotValidException.class) {
						fails.setId(true);
					} else if (error.getClass().equals(NameNotValidException.class)) {
						fails.setName(true);
					} else if (error.getClass().equals(IntroducedNotValidException.class)) {
						fails.setIntroduced(true);
					} else if (error.getClass().equals(DiscontinuedNotValidException.class)) {
						fails.setDiscontinued(true);
					} else if (error.getClass().equals(DateIntervalNotValidException.class)) {
						fails.setInterval(true);
					} else if (error.getClass().equals(CompanyIdNotValidException.class)) {
						fails.setCompany(true);
					}
				}
				
				ArrayList<Company> companies = new ArrayList<Company>();
				try {
					companies = companyService.getAllCompanies();
				} catch (SQLException sqle) {
					LOGGER.error("Oups erreur SQL");
				}
				
				ModelAndView response = new ModelAndView("editComputer");
				
				if (dto.getId().equals("0")) {
					response.addObject("operation", "Add");
				} else {
					response.addObject("operation", "Edit");
				}
				response.addObject("companies", companies);
				response.addObject("computer", dto);
				response.addObject("errors", fails);
				
				return response;
			}
		} catch (SQLException sqle) {
			LOGGER.error(sqle);
		}
    	
		return new ModelAndView("redirect:/dashboard");
    }
	
    private Computer getComputer(String string) throws SQLException {
		try {
			int id = Integer.parseInt(string);
			return computerService.getComputer(id).orElse(null);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
