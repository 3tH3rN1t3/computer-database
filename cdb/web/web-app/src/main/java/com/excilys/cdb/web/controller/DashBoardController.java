package com.excilys.cdb.web.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.OrderBy;
import com.excilys.cdb.model.Session;
import com.excilys.cdb.model.SearchBy;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.web.dto.Locale;
import com.excilys.cdb.web.mapper.WebComputerMapper;

@Controller
@Scope("session")
public class DashBoardController {
	
	private ComputerService computerService;
	
	private WebComputerMapper computerMapper;
	
	private LocaleResolver localeResolver;
	
	private Session session;
	
    public DashBoardController(ComputerService computerService, WebComputerMapper computerMapper, LocaleResolver localeResolver) {
    	this.computerService = computerService;
    	this.computerMapper = computerMapper;
    	this.localeResolver = localeResolver;
    	session = new Session();
    }
    
    @RequestMapping(value="/test")
    @ResponseBody
    public String test() {
    	System.out.println("YESSS");
    	return "Test ok";
    }
    
    @GetMapping(value="/dashboard")
    @ResponseBody
    public ModelAndView dashboard( HttpServletRequest request
    		, @RequestParam(required = false) String pageNum, @RequestParam(required = false) String itemsPerPage
    		, @RequestParam(required = false) String searchBy, @RequestParam(required = false) String search
    		, @RequestParam(required = false) String orderBy, @RequestParam(required = false) String order) {
    	
    	List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    	this.setSearch(session, searchBy, search);
    	this.setOrder(session, orderBy, order);
		this.setItemsPerPage(session, itemsPerPage);
		this.setPage(session, pageNum);
		
		Sort sort;
		if (session.getOrderBy() == OrderBy.ID) {
			sort = Sort.by(Direction.valueOf(session.getOrder().toUpperCase()), "id");
		} else {
			if (session.getOrder().equals("ASC")) {
				sort = JpaSort.unsafe(Direction.ASC, "(COALESCE("+session.getOrderBy().getColumn()+", 'zzzzzzzzzzzzzz'))", "(computer.id)");
			} else {
				sort = JpaSort.unsafe(Direction.DESC, "("+session.getOrderBy().getColumn()+")", "(-computer.id)");
			}
		}
		
		Pageable pageRequest = PageRequest.of(session.getNumPage()-1, session.getMaxItems(), sort);
		Page<Computer> computers = this.computerService.search(pageRequest, session.getSearchBy(), session.getSearch());
		
		ModelAndView response = new ModelAndView("dashboard");
		response.addObject("computers", computerMapper.toComputerDTOArray(computers.getContent()));
		response.addObject("session", session);
		response.addObject("admin", roles.size() > 0 ? roles.get(0).getAuthority().equals("ROLE_ADMIN") : false);
		response.addObject("searches", SearchBy.values());
		response.addObject("languages", Locale.values());
		return response;
    }
	
	private void setSearch(Session p, String searchBy, String search) {
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
			if (search.isEmpty()) {
				p.setSearchBy(SearchBy.NAME);
			}
		}
		p.setTotalItems(computerService.count(p.getSearchBy(), p.getSearch()));
	}
	
	private void setPage(Session session, String pageNumber) {
		try {
			int page = Integer.parseInt(pageNumber);
			session.setNumPage(page);
		} catch (Exception e) {
		}
		if (session.getNumPage() > session.getMaxPage()) {
			session.setNumPage(session.getMaxPage());
		}
		if (session.getNumPage() <= 0) {
			session.setNumPage(1);
		}
	}
	
	private void setItemsPerPage(Session session, String itemsPerPage) {
		try {
			int items = Integer.parseInt(itemsPerPage);
			if (items > 0) {
				session.setNumPage(1);
				session.setMaxItems(items);
			}
		} catch (Exception e) {
		}
	}
	
	private void setOrder(Session session, String orderBy, String order) {
		if (orderBy != null && !session.getOrderBy().toString().equalsIgnoreCase(orderBy)) {
			session.setNumPage(1);
			try {
				session.setOrderBy(OrderBy.valueOf(orderBy.toUpperCase()));
			} catch (IllegalArgumentException e) {
				session.setOrderBy(OrderBy.ID);
			}
		}
		if (order != null) {
			try {
				session.setOrder(com.excilys.cdb.model.Order.valueOf(order.toUpperCase()));
			} catch (IllegalArgumentException e) {
				session.setOrder(com.excilys.cdb.model.Order.ASC);
			}
		}
	}

}
