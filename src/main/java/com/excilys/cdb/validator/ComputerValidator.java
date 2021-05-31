package com.excilys.cdb.validator;

import java.time.LocalDate;

import com.excilys.cdb.dto.DBComputerDTO;
import com.excilys.cdb.exceptions.DateFormatNotValidException;
import com.excilys.cdb.exceptions.DateIntervalNotValidException;
import com.excilys.cdb.exceptions.IdNotValidException;
import com.excilys.cdb.exceptions.NameNotValidException;

public class ComputerValidator {
	
	private static ComputerValidator instance;
	
	private ComputerValidator() {}
	
	public static ComputerValidator getInstance() {
		if (instance == null) {
			instance = new ComputerValidator();
		}
		return instance;
	}
	
	public void validate(DBComputerDTO dto) {
		this.validateId(dto.getId());
		this.validateName(dto.getName());
		this.validateDate(dto.getIntroduced());
		this.validateDate(dto.getDiscontinued());
		this.validateDateInterval(dto.getIntroduced(), dto.getDiscontinued());
		//this.validateCompany(dto.getCompany());
	}
	
	private void validateId(String id) {
		try {
			int num = Integer.parseInt(id);

			if (num <= 0) {
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

	private void validateDate(String date) {
		try {
			if (date != null) {
				LocalDate lDate = LocalDate.parse(date);
				if (lDate.isBefore(LocalDate.parse("1970-01-01")) || lDate.isAfter(LocalDate.parse("2038-01-19"))) {
					throw new DateFormatNotValidException("Date Format not valid : " + date);
				}
			}

		} catch (Exception e) {
			throw new DateFormatNotValidException("Date Format not valid : " + date);
		}

	}

	private void validateDateInterval(String introduced, String discontinued) {
		
	
			if ( introduced != null && discontinued != null ) {
				if (!LocalDate.parse(introduced).isBefore(LocalDate.parse(discontinued))) {
					throw new DateIntervalNotValidException("Date Interval not valid : " + introduced + " > " + discontinued);
				}
			}


	}
	
	private void ValidateCompany() {
		
	}
	
	private void validateCompanyId(String id) {
		if (id != null) {
			validateId(id);
		}
	}
}
