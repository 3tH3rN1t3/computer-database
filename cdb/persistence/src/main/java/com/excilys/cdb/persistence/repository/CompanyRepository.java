package com.excilys.cdb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.persistence.dto.DBCompanyDTO;

@Repository
public interface CompanyRepository extends JpaRepository<DBCompanyDTO, Integer> {
}
