package com.excilys.cdb.persistence.repository;

import com.excilys.cdb.persistence.dto.DBComputerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends JpaRepository<DBComputerDTO, Integer> {
	
	@Query("SELECT computer FROM DBComputerDTO computer LEFT JOIN DBCompanyDTO company ON computer.company = company WHERE computer.name LIKE %:filter%")
	Page<DBComputerDTO> findByName(Pageable p, @Param(value="filter") String filter);
	
	@Query("SELECT computer FROM DBComputerDTO computer LEFT JOIN DBCompanyDTO company ON computer.company = company WHERE computer.introduced LIKE %:filter%")
	Page<DBComputerDTO> findByIntroduced(Pageable p, @Param(value="filter") String filter);
	
	@Query("SELECT computer FROM DBComputerDTO computer LEFT JOIN DBCompanyDTO company ON computer.company = company WHERE computer.discontinued LIKE %:filter%")
	Page<DBComputerDTO> findByDiscontinued(Pageable p, @Param(value="filter") String filter);
	
	@Query("SELECT computer FROM DBComputerDTO computer LEFT JOIN DBCompanyDTO company ON computer.company = company WHERE company.name LIKE %:filter%")
	Page<DBComputerDTO> findByCompanyName(Pageable p, @Param(value="filter") String filter);
	
	int countByNameContaining(String filter);
	
	int countByIntroducedContaining(String filter);
	
	int countByDiscontinuedContaining(String filter);
	
	int countByCompanyNameContaining(String filter);
}
