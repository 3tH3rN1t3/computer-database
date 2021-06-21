package com.excilys.cdb.persistence.converter;

import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return attribute;
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.isEmpty()) {
			return null;
		} else {
			return LocalDate.parse(dbData.substring(0, 10)).toString();
		}
	}

}
