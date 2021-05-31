package com.excilys.cdb.exceptions;

public class DateIntervalNotValidException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DateIntervalNotValidException(String message) {
		super(message);
	}
}
