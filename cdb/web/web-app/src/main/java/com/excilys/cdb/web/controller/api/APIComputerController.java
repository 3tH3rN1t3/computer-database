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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Order;
import com.excilys.cdb.model.OrderBy;
import com.excilys.cdb.model.SearchBy;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.web.dto.WebComputerDTO;
import com.excilys.cdb.web.mapper.WebComputerMapper;

@RestController
@RequestMapping("api/computers")
public class APIComputerController {
	
	private ComputerService computerService;
	
	private WebComputerMapper computerMapper;
	
	public APIComputerController(ComputerService computerService, WebComputerMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}
	
	@GetMapping("test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("TEST OK");
	}
	
	@GetMapping("list")
	public ResponseEntity<List<WebComputerDTO>> getPage(@RequestParam(value="search", defaultValue="") String search
			, @RequestParam(value="searchby", defaultValue="NAME") SearchBy searchBy
			, @RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="items", defaultValue="10") int items
			, @RequestParam(value="orderby", defaultValue="ID") OrderBy orderBy, @RequestParam(value="order", defaultValue="ASC") Order order) {
		
		Sort sort;
		if (orderBy == OrderBy.ID) {
			sort = Sort.by(Direction.valueOf(order.toString()), "id");
		} else {
			if (order.toString().equals("ASC")) {
				sort = JpaSort.unsafe(Direction.ASC, "(COALESCE("+orderBy.getColumn()+", 'zzzzzzzzzzzzzz'))", "(computer.id)");
			} else {
				sort = JpaSort.unsafe(Direction.DESC, "("+orderBy.getColumn()+")", "(-computer.id)");
			}
		}
		Pageable pageRequest = PageRequest.of(page-1 < 0 ? 0 : page-1, items, sort);
		Page<Computer> computers = this.computerService.search(pageRequest, searchBy, search);
		return ResponseEntity.ok(computerMapper.toComputerDTOArray(computers.getContent()));
	}
	
	@GetMapping("computer/{id}")
	public ResponseEntity<WebComputerDTO> get(@PathVariable int id) {
		return ResponseEntity.of(computerMapper.toComputerDTO(computerService.getComputer(id)));
	}
	
	@GetMapping("count")
	public ResponseEntity<Integer> count(@RequestParam(
			value="search", defaultValue="") String search, @RequestParam(value="searchby", defaultValue="NAME") SearchBy searchBy
			) {
		return ResponseEntity.ok(computerService.count(searchBy, search));
	}
	
	@PutMapping("add")
	public ResponseEntity<WebComputerDTO> create(@RequestParam(name="name") String name,
			@RequestParam(name="introduced", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate introduced,
			@RequestParam(name="discontinued", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate discontinued,
			@RequestParam(name="companyId", defaultValue="") String companyId) {
		WebComputerDTO dto = new WebComputerDTO.WebComputerDTOBuilder("0", name)
				.withIntroduced(introduced == null ? null : introduced.toString())
				.withDiscontinued(discontinued == null ? null : discontinued.toString())
				.withCompanyId(companyId).build();
		return ResponseEntity.ok(computerMapper.toComputerDTO(computerService.addComputer(computerMapper.toComputer(dto))));
	}
	
	@PutMapping("update")
	public ResponseEntity<WebComputerDTO> update(@RequestParam(name="id") int id, @RequestParam(name="name") String name,
			@RequestParam(name="introduced", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate introduced,
			@RequestParam(name="discontinued", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate discontinued,
			@RequestParam(name="companyId", defaultValue="") String companyId) {
		WebComputerDTO dto = new WebComputerDTO.WebComputerDTOBuilder(""+id, name)
				.withIntroduced(introduced == null ? null : introduced.toString())
				.withDiscontinued(discontinued == null ? null : discontinued.toString())
				.withCompanyId(companyId).build();
		return ResponseEntity.ok(computerMapper.toComputerDTO(computerService.addComputer(computerMapper.toComputer(dto))));
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<Integer> delete(@RequestParam(name="id") int id) {
		computerService.deleteComputer(id);
		return ResponseEntity.ok(id);
	}
}