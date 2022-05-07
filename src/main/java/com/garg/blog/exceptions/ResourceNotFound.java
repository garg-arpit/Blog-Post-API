package com.garg.blog.exceptions;

public class ResourceNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String filedName;
	int fieldValue;

	public ResourceNotFound(String resourceName, String filedName, int fieldValue) {
		super(resourceName + " not found with " + filedName + " : " + fieldValue);
		this.resourceName = resourceName;
		this.filedName = filedName;
		this.fieldValue = fieldValue;
	}

}
