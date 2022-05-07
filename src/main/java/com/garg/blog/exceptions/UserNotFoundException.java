package com.garg.blog.exceptions;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
