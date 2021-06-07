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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.config.TestConfig;
import com.excilys.cdb.mapper.WebComputerMapper;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerService computerService;
	
	private static final Logger LOGGER = LogManager.getLogger(EditComputerServlet.class);
	
	public DeleteComputerServlet() {
    }
	
	public void init() {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext(TestConfig.class);
    	computerService = (ComputerService) ctx.getBean("computerService");
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
