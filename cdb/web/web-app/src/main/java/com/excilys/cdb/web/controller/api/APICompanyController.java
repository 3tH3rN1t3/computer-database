package com.excilys.cdb.web.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.web.dto.WebCompanyDTO;
import com.excilys.cdb.web.mapper.WebCompanyMapper;

@RestController
@RequestMapping("api/companies")
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
		return ResponseEntity.of(companyMapper.toCompanyDTO(companyService.getCompanyById(id)));
	}
	
	@GetMapping("count")
	public ResponseEntity<Integer> count() {
		return ResponseEntity.ok(companyService.countCompanies());
	}
}