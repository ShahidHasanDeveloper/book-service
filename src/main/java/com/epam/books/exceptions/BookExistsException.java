package com.epam.books.exceptions;

public class BookExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public BookExistsException(String message) {
		super(message);
	}
}
