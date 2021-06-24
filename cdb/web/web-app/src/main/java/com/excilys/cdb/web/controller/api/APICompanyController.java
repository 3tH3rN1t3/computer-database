package com.excilys.cdb.web.controller.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.web.dto.WebCompanyDTO;
import com.excilys.cdb.web.mapper.WebCompanyMapper;

@RestController
@RequestMapping("api/company")
public class APICompanyController {
	
	private CompanyService companyService;
	
	private WebCompanyMapper companyMapper;
	
	public APICompanyController(CompanyService companyService, WebCompanyMapper companyMapper) {
		this.companyService = companyService;
		this.companyMapper = companyMapper;
	}
	
	@GetMapping("test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("TEST OK");
	}
	
	@GetMapping("list")
	public ResponseEntity<List<WebCompanyDTO>> getList() {
		return ResponseEntity.ok(companyMapper.toCompanyDTOArray(companyService.getAllCompanies()));
	}
	
	@GetMapping("company/{id}")
	public ResponseEntity<WebCompanyDTO> get(@PathVariable int id) {
		return ResponseEntity.ok(companyMapper.toCompanyDTO(companyService.getCompanyById(id)).orElse(null));
	}
	
	@GetMapping("count")
	public ResponseEntity<Integer> count() {
		return ResponseEntity.ok(companyService.countCompanies());
	}
}