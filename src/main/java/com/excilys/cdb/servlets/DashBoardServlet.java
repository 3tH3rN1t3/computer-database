package com.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import  org.apache.logging.log4j.Logger;

import com.excilys.cdb.mapper.WebComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Order;
import com.excilys.cdb.model.OrderBy;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/dashboard")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService service;
	private static final Logger LOGGER = LogManager.getLogger(DashBoardServlet.class);
	
    public DashBoardServlet() throws IOException {
        service = ComputerService.getInstance();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);
			
			String search = (String) session.getAttribute("search");
			if (search == null) {
				search = "";
			}
			if (request.getParameter("search") != null) {
				search = request.getParameter("search");
			}
			
			Pagination page = (Pagination) session.getAttribute("page");
			if (page == null) {
				page = new Pagination(service.countComputers(search));
			}
			
			this.setItemsPerPage(page, request.getParameter("itemsPerPage"));
			this.setPage(page, request.getParameter("page"));
			
			this.setOrder(page, request.getParameter("orderby"), request.getParameter("order"));
			
			ArrayList<Computer> computers = new ArrayList<Computer>();
			computers = this.getComputers(page, search);
			
			session.setAttribute("page", page);
			session.setAttribute("search", search);
			
			request.setAttribute( "nombrePageMax", page.getMaxPage() );
			request.setAttribute( "computers", WebComputerMapper.getInstance().toComputerDTOs(computers));
			request.setAttribute( "page", page);//Trnasformation en DTO?
			request.setAttribute("search", search);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
		} catch (SQLException e) {
			LOGGER.error("Oups erreur SQL", e);;
		}
	}
	
	private void setPage(Pagination p, String pageNumber) {
		try {
			int page = Integer.parseInt(pageNumber);
			p.setNumPage(page);
		} catch (Exception e) {
		}
		if (p.getNumPage() <= 0 || p.getNumPage() > p.getMaxPage()) {
			p.setNumPage(1);
		}
	}
	
	private void setOrder(Pagination p, String orderBy, String order) {
		if (orderBy != null && !p.getOrderBy().toString().equalsIgnoreCase(orderBy)) {
			p.setNumPage(1);
			try {
				p.setOrderBy(OrderBy.valueOf(orderBy.toUpperCase()));
			} catch (IllegalArgumentException e) {
				p.setOrderBy(OrderBy.ID);
			}
		}
		if (order != null) {
			try {
				p.setOrder(Order.valueOf(order.toUpperCase()));
			} catch (IllegalArgumentException e) {
				p.setOrder(Order.ASC);;
			}
		}
	}
	
	private void setItemsPerPage(Pagination p, String itemsPerPage) {
		try {
			int items = Integer.parseInt(itemsPerPage);
			if (items > 0) {
				p.setNumPage(1);
				p.setMaxItems(items);
			}
		} catch (Exception e) {
		}
	}
	
	private ArrayList<Computer> getComputers(Pagination p, String search) throws SQLException {
		ArrayList<Computer> listcomputer = new ArrayList<Computer>();
		
		if (search != null && !"".equals(search)) {
			p.setNumPage(1);
		}
		p.setTotalItems(service.countComputers(search));
		listcomputer = service.search(search, p);
		return listcomputer;
	
	}

}
