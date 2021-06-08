package com.excilys.cdb.servlet;

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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.TestConfig;
import com.excilys.cdb.mapper.WebComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Order;
import com.excilys.cdb.model.OrderBy;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.SearchBy;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/dashboard")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerService service;
	
	private WebComputerMapper computerMapper;
	
	private static final Logger LOGGER = LogManager.getLogger(DashBoardServlet.class);
	
    public DashBoardServlet() {
    }
    
    public void init() {
    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TestConfig.class);
    	service = (ComputerService) ctx.getBean("computerService");
    	computerMapper = (WebComputerMapper) ctx.getBean("webComputerMapper");
    	ctx.close();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);
			
			Page page = (Page) session.getAttribute("page");
			if (page == null) {
				page = new Page();
			}
			
			this.setSearch(page, request.getParameter("searchby"), request.getParameter("search"));
			this.setItemsPerPage(page, request.getParameter("itemsPerPage"));
			this.setOrder(page, request.getParameter("orderby"), request.getParameter("order"));
			this.setPage(page, request.getParameter("page"));
			
			ArrayList<Computer> computers = new ArrayList<Computer>();
			computers = this.getComputers(page);
			
			session.setAttribute("page", page);
			
			request.setAttribute( "nombrePageMax", page.getMaxPage() );
			request.setAttribute( "computers", computerMapper.toComputerDTOArray(computers));
			request.setAttribute( "page", page);//Trnasformation en DTO?
			request.setAttribute("searches", SearchBy.values());
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
		} catch (SQLException e) {
			LOGGER.error("Oups erreur SQL", e);;
		}
	}
	
	private void setSearch(Page p, String searchBy, String search) throws SQLException {
		if (searchBy != null) {
			p.setNumPage(1);
			try {
				p.setSearchBy(SearchBy.valueOf(searchBy.toUpperCase()));
			} catch (IllegalArgumentException e) {
				p.setSearchBy(SearchBy.NAME);
			}
		}
		if (search != null) {
			p.setNumPage(1);
			p.setSearch(search);
		}
		p.setTotalItems(service.countComputers(p));
	}
	
	private void setPage(Page p, String pageNumber) {
		try {
			int page = Integer.parseInt(pageNumber);
			p.setNumPage(page);
		} catch (Exception e) {
		}
		if (p.getNumPage() <= 0 || p.getNumPage() > p.getMaxPage()) {
			p.setNumPage(1);
		}
	}
	
	private void setItemsPerPage(Page p, String itemsPerPage) {
		try {
			int items = Integer.parseInt(itemsPerPage);
			if (items > 0) {
				p.setNumPage(1);
				p.setMaxItems(items);
			}
		} catch (Exception e) {
		}
	}
	
	private ArrayList<Computer> getComputers(Page p) throws SQLException {
		ArrayList<Computer> listcomputer = new ArrayList<Computer>();
		listcomputer = service.search(p);
		return listcomputer;
	
	}
	
	private void setOrder(Page p, String orderBy, String order) {
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
				p.setOrder(Order.ASC);
			}
		}
	}

}
