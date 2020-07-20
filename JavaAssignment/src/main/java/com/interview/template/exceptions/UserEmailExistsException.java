package com.interview.template.exceptions;

public class UserEmailExistsException extends Exception {
	
	private static final long serialVersionUID = 4507254628220657407L;

	public UserEmailExistsException(String message) {
		super(message);
	}
}
