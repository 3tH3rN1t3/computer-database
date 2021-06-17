package com.excilys.cdb.persistence.repository;

import com.excilys.cdb.model.Computer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Integer> {
	
	@Query("SELECT computer FROM Computer computer LEFT JOIN Company company ON computer.company = company WHERE computer.name LIKE %:filter%")
	Page<Computer> findAllByName(Pageable p, @Param(value="filter") String filter);
	
	@Query("SELECT computer FROM Computer computer LEFT JOIN Company company ON computer.company = company WHERE computer.introduced LIKE %:filter%")
	Page<Computer> findAllByIntroduced(Pageable p, @Param(value="filter") String filter);
	
	@Query("SELECT computer FROM Computer computer LEFT JOIN Company company ON computer.company = company WHERE computer.discontinued LIKE %:filter%")
	Page<Computer> findAllByDiscontinued(Pageable p, @Param(value="filter") String filter);
	
	@Query("SELECT computer FROM Computer computer LEFT JOIN Company company ON computer.company = company WHERE company.name LIKE %:filter%")
	Page<Computer> findAllByCompany(Pageable p, @Param(value="filter") String filter);
}
