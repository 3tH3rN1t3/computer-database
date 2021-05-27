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
import  org.apache.logging.log4j.Logger;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/dashboard")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService service;
	private String search;
	private Pagination page;
	private static final Logger LOGGER = LogManager.getLogger(DashBoardServlet.class);
	
    public DashBoardServlet() throws IOException {
        service = ComputerService.getInstance();
        search = "";
        page = new Pagination();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.setItemsPerPage(request.getParameter("itemsPerPage"));
		this.setPage(request.getParameter("page"));
		
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try {
			computers = this.getComputers(request.getParameter("search"));
		} catch (SQLException e) {
			LOGGER.error("Oups erreur sql", e);;
		}
		
		
		request.setAttribute( "nombrePageMax", page.getMaxPage() );
		request.setAttribute( "computers", computers );
		request.setAttribute( "page", page);
		request.setAttribute("search", search);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void setPage(String string) {
		try {
			int page = Integer.parseInt(string);
			this.page.setNumPage(page);
		} catch (Exception e) {
		}
		if (this.page.getNumPage() <= 0 || this.page.getNumPage() > this.page.getMaxPage()) {
			this.page.setNumPage(1);
		}
	}
	
	private void setItemsPerPage(String string) {
		try {
			int items = Integer.parseInt(string);
			if (items > 0) {
				page.setNumPage(1);
				page.setMaxItems(items);
			}
		} catch (Exception e) {
		}
	}
	
	private ArrayList<Computer> getComputers(String paramSearch) throws SQLException {
		ArrayList<Computer> listcomputer = new ArrayList<Computer>(); 
		if (paramSearch != null) {
			page.setNumPage(1);
			search = paramSearch;
		}
		
		
		if (this.search == "") {
			page.setTotalItems(service.countComputers());
			listcomputer = service.getComputers(page);
		}else {
			page.setTotalItems(service.countComputers(search));
			listcomputer = service.search(search, page);
			
		}
		return listcomputer;
	
	}

}
