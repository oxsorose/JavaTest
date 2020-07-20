package com.interview.template.exceptions;

public class UserNameExistsException extends Exception {
	
	private static final long serialVersionUID = 4507254628220657407L;

	public UserNameExistsException(String message) {
		super(message);
	}
}
