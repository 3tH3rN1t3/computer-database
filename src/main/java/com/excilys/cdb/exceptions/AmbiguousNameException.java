package com.excilys.cdb.exceptions;

public class AmbiguousNameException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public AmbiguousNameException(String message) {
		super(message);
	}

}
