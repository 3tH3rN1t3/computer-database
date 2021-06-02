package com.excilys.cdb.exceptions;

public class ValidatorException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ValidatorException() {
		
	}
	
	public ValidatorException(String message) {
		super(message);
	}
}
