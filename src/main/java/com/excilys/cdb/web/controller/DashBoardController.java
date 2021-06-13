package com.excilys.cdb.web.controller;

import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import  org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Order;
import com.excilys.cdb.model.OrderBy;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.SearchBy;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.web.dto.Locale;
import com.excilys.cdb.web.mapper.WebComputerMapper;

@Controller
@Scope("session")
public class DashBoardController {
	
	@Autowired
	private ComputerService service;
	
	@Autowired
	private WebComputerMapper computerMapper;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	private Page page;
	
	private static final Logger LOGGER = LogManager.getLogger(DashBoardController.class);
	
    public DashBoardController() {
    	page = new Page();
    }
    
    @RequestMapping(value="/test")
    @ResponseBody
    public String test() {
    	System.out.println("YESSS");
    	return "Test ok";
    }
    
    @GetMapping(value="/dashboard")
    @ResponseBody
    public ModelAndView dashboard(ServletRequest request) {
		this.setSearch(page, request.getParameter("searchby"), request.getParameter("search"));
		this.setItemsPerPage(page, request.getParameter("itemsPerPage"));
		this.setOrder(page, request.getParameter("orderby"), request.getParameter("order"));
		this.setPage(page, request.getParameter("page"));
		
		ArrayList<Computer> computers = new ArrayList<Computer>();
		computers = this.getComputers(page);
		
		ModelAndView response = new ModelAndView("dashboard");
		response.addObject( "computers", computerMapper.toComputerDTOArray(computers));
		response.addObject( "page", page);
		response.addObject("searches", SearchBy.values());
		response.addObject("languages", Locale.values());
		response.addObject("lang", getLocale(request));
		return response;
    }
	
	private void setSearch(Page p, String searchBy, String search) {
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
		if (p.getNumPage() > p.getMaxPage()) {
			p.setNumPage(p.getMaxPage());
		}
		if (p.getNumPage() <= 0) {
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
	
	private ArrayList<Computer> getComputers(Page p) {
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
	
	private Locale getLocale(ServletRequest request) {
		try {
			return Locale.valueOf(localeResolver.resolveLocale((HttpServletRequest) request).toString().toUpperCase());
		} catch (IllegalArgumentException e) {
			return Locale.FR;
		}
	}

}
