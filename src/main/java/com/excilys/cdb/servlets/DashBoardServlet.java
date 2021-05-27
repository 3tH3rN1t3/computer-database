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
import com.excilys.cdb.persistence.ComputerDAO;

@WebServlet("/dashboard")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerDAO dao;
	private String search;
	private Pagination page;
	private static final Logger LOGGER = LogManager.getLogger(DashBoardServlet.class);
	
    public DashBoardServlet() throws IOException {
        dao = ComputerDAO.getInstance();
        search = "";
        page = new Pagination();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.setPage( request.getParameter("page"));
		
		this.setItemsPerPage(request.getParameter("nombreElementPage"));
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
			if (page <= 0 || page > this.page.getMaxPage()) {
				page = this.page.getMaxPage();
			}
			this.page.setNumPage(page);
		} catch (Exception e) {
		}
	}
	
	private void setItemsPerPage(String string) {
		try {
			int items = Integer.parseInt(string);
			if (items > 0) {
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
			page.setTotalItems(dao.CountComputers());
			listcomputer = dao.getComputersPerPage(page);
		}else {
			page.setTotalItems(dao.CountComputers(search));
			listcomputer = dao.getComputersBySearch(search, page);
			
		}
		return listcomputer;
	
	}

}
