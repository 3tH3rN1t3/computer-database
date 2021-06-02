package com.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService;
	private CompanyService companyService;
	private static final Logger LOGGER = LogManager.getLogger(EditComputerServlet.class);
	
    public EditComputerServlet() throws IOException {
        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Computer computer = null;
		ArrayList<Company> companies = new ArrayList<Company>();
		try {
			computer = getComputer(request.getParameter("id"));
			companies = companyService.getAllCompanies();
		} catch (SQLException e) {
			LOGGER.error("Oups erreur SQL");
		}
		
		if (computer != null) {
			request.setAttribute("computer", WebComputerMapper.getInstance().toComputerDTO(computer));
		}
		request.setAttribute("companies", WebCompanyMapper.getInstance().toCompanyDTOs(companies));
		if (computer == null) {
			request.setAttribute("operation", "Add");
		} else {
			request.setAttribute("operation", "Edit");
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/editComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			WebComputerDTO dto = new WebComputerDTO.ComputerDTOBuilder(request.getParameter("id"), request.getParameter("computerName"))
					.withIntroduced(request.getParameter("introduced") == "" ? null : request.getParameter("introduced"))
					.withDiscontinued(request.getParameter("discontinued") == "" ? null : request.getParameter("discontinued"))
					.withCompany(request.getParameter("companyId") == "" ? null : new WebCompanyDTO(request.getParameter("companyId"), "company"))
					.build();
			try {
				Computer computer = WebComputerMapper.getInstance().toComputer(dto);
				if (computer.getId() == 0) {
					computerService.addComputer(computer);
				} else {
					computerService.updateComputer(computer);
				}
				response.sendRedirect("dashboard");
				
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
						fails.setDiscontinued(true);
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
				
				if (dto.getId().equals("0")) {
					request.setAttribute("operation", "Add");
				} else {
					request.setAttribute("operation", "Edit");
				}
				request.setAttribute("companies", companies);
				request.setAttribute("computer", dto);
				request.setAttribute("errors", fails);
				
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/editComputer.jsp").forward(request, response);
			}
		} catch (SQLException sqle) {
			LOGGER.error(sqle);
		} catch (IOException ioe) {
			LOGGER.fatal(ioe);
		}
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
