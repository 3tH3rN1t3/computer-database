package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.excilys.cdb.config.TestConfig;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerService computerService;
	
	private static final Logger LOGGER = LogManager.getLogger(EditComputerServlet.class);
	
	public DeleteComputerServlet() {
    }
	
	public void init() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TestConfig.class);
    	computerService = (ComputerService) ctx.getBean("computerService");
    	ctx.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String values = request.getParameter("selection");
		Scanner scanner = new Scanner(values).useDelimiter(",");
		while(scanner.hasNextInt()) {
			int id = scanner.nextInt();
			System.out.println(id);
			try {
				computerService.deleteComputer(id);
			} catch (SQLException e) {
				LOGGER.error("Oups, erreur SQL");
			}
		}
		scanner.close();
	}

}
