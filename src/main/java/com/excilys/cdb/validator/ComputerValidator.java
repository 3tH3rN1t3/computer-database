package com.excilys.cdb.validator;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import com.excilys.cdb.dto.WebCompanyDTO;
import com.excilys.cdb.dto.WebComputerDTO;
import com.excilys.cdb.exceptions.CompanyIdNotValidException;
import com.excilys.cdb.exceptions.DateFormatNotValidException;
import com.excilys.cdb.exceptions.DateIntervalNotValidException;
import com.excilys.cdb.exceptions.DiscontinuedNotValidException;
import com.excilys.cdb.exceptions.IdNotValidException;
import com.excilys.cdb.exceptions.IntroducedNotValidException;
import com.excilys.cdb.exceptions.NameNotValidException;
import com.excilys.cdb.exceptions.ValidatorException;
import com.excilys.cdb.service.CompanyService;

public class ComputerValidator {
	
	private static ComputerValidator instance;
	
	private ComputerValidator() {}
	
	public static ComputerValidator getInstance() {
		if (instance == null) {
			instance = new ComputerValidator();
		}
		return instance;
	}
	
	public void validate(WebComputerDTO dto) throws SQLException, IOException {
		ValidatorException ex = new ValidatorException();
		try {
			this.validateId(dto.getId());
		} catch (ValidatorException e) {
			ex.addSuppressed(e);
		}
		try {
			this.validateName(dto.getName());
		} catch (ValidatorException e) {
			ex.addSuppressed(e);
		}
		try {
			this.validateDate(dto.getIntroduced());
		} catch (ValidatorException e) {
			IntroducedNotValidException ie = new IntroducedNotValidException(e.getMessage());
			ex.addSuppressed(ie);
		}
		try {
			this.validateDate(dto.getDiscontinued());
		} catch (ValidatorException e) {
			DiscontinuedNotValidException de = new DiscontinuedNotValidException(e.getMessage());
			ex.addSuppressed(de);
		}
		try {
			this.validateDateInterval(dto.getIntroduced(), dto.getDiscontinued());
		} catch (ValidatorException e) {
			ex.addSuppressed(e);
		}
		try {
			this.validateCompany(dto.getCompany());
		} catch (ValidatorException e) {
			ex.addSuppressed(e);
		}
		if (ex.getSuppressed().length > 0) {
			throw ex;
		}
	}

	private void validateId(String id) {
		try {
			int num = Integer.parseInt(id);
			if (num < 0) {
				throw new IdNotValidException("Id not valid : " + id);
			}
		} catch (Exception e) {
			throw new IdNotValidException("Id not valid : " + id);
			
		}
	}

	private void validateName(String name) {
		if (name == null || name.isBlank()) {
			throw new NameNotValidException("Name not valid : " + name);
		}
	}

	private void validateDate(Optional<String> date) {
		if (date.isPresent()) {
			try {
				LocalDate lDate = LocalDate.parse(date.get());
				if (lDate.isBefore(LocalDate.parse("1970-01-01")) || lDate.isAfter(LocalDate.parse("2038-01-19"))) {
					throw new DateFormatNotValidException("Date Format not valid : " + date);
				}
			} catch (Exception e) {
				throw new DateFormatNotValidException("Date Format not valid : " + date);
			}
		}
	}

	private void validateDateInterval(Optional<String> introduced, Optional<String> discontinued) {
		if (introduced.isPresent() && discontinued.isPresent() ) {
			if (!LocalDate.parse(introduced.get()).isBefore(LocalDate.parse(discontinued.get()))) {
				throw new DateIntervalNotValidException("Date Interval not valid : " + introduced.get() + " > " + discontinued.get());
			}
		}
	}

	private void validateCompany(Optional<WebCompanyDTO> company) throws SQLException, IOException {
		if (company.isPresent()) {
			try {
				this.validateId(company.get().getId());
				int id = Integer.parseInt(company.get().getId());
				if (!CompanyService.getInstance().getCompanyById(id).isPresent()) {
					throw new CompanyIdNotValidException("Company Id not valid : " + id);
				}
			} catch (IdNotValidException e) {
				throw new CompanyIdNotValidException("Company Id not valid : " + company.get().getId());
			}
		}
	}
}
