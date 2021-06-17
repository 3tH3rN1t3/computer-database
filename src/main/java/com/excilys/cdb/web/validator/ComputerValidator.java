package com.excilys.cdb.web.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.web.dto.WebComputerDTO;

@Component
@Scope("singleton")
public class ComputerValidator implements Validator {
	
	private CompanyService companyService;
	
	private ComputerValidator(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return WebComputerDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		WebComputerDTO dto = (WebComputerDTO) target;
		
		this.validateId(dto, errors);
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "name");
		
		this.validateIntroduced(dto, errors);
		
		this.validateDiscontinued(dto, errors);
		
		this.validateDateInterval(dto, errors);
		
		this.validateCompanyId(dto, errors);
	}

	private void validateId(WebComputerDTO dto, Errors errors) {
		try {
			int id = Integer.parseInt(dto.getId());
			if (id < 0) {
				errors.rejectValue("id", "", "id");
			}
		} catch (NumberFormatException e) {
			errors.rejectValue("id", "", "id");
		}
	}

	private void validateIntroduced(WebComputerDTO dto, Errors errors) {
		String introduced = dto.getIntroduced();
		if (introduced != null && !introduced.isEmpty()) {
			try {
				LocalDate lDate = LocalDate.parse(introduced);
				if (lDate.isBefore(LocalDate.parse("1970-01-01")) || lDate.isAfter(LocalDate.parse("2038-01-19"))) {
					errors.rejectValue("introduced", "", "introduced");
				}
			} catch (DateTimeParseException e) {
				errors.rejectValue("introduced", "", "introduced");
			}
		}
	}

	private void validateDiscontinued(WebComputerDTO dto, Errors errors) {
		String discontinued = dto.getDiscontinued();
		if (discontinued != null && !discontinued.isEmpty()) {
			try {
				LocalDate lDate = LocalDate.parse(discontinued);
				if (lDate.isBefore(LocalDate.parse("1970-01-01")) || lDate.isAfter(LocalDate.parse("2038-01-19"))) {
					errors.rejectValue("discontinued", "", "discontinued");
				}
			} catch (DateTimeParseException e) {
				errors.rejectValue("discontinued", "", "discontinued");
			}
		}
	}

	private void validateDateInterval(WebComputerDTO dto, Errors errors) {
		if (dto.getIntroduced() != null && !dto.getIntroduced().isBlank() && dto.getDiscontinued() != null && !dto.getDiscontinued().isBlank() ) {
			if (!LocalDate.parse(dto.getIntroduced()).isBefore(LocalDate.parse(dto.getDiscontinued()))) {
				errors.rejectValue("discontinued", "", "interval");
			}
		}
	}

	private void validateCompanyId(WebComputerDTO dto, Errors errors) {
		if (dto.getCompanyId() != null && !dto.getCompanyId().isBlank()) {
			try {
				int id = Integer.parseInt(dto.getCompanyId());
				if (!companyService.getCompanyById(id).isPresent()) {
					errors.rejectValue("companyId", "", "companyId");
				}
			} catch (NumberFormatException e) {
				errors.rejectValue("companyId", "", "companyId");
			}
		}
	}
}
