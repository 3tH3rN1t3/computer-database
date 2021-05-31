package com.excilys.cdb.exceptions;

public class DateFormatNotValidException  extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public  DateFormatNotValidException(String message) {
        super(message);
    }

}