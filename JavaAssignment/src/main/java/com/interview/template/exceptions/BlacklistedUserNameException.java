package com.interview.template.exceptions;

public class BlacklistedUserNameException extends Exception {
	
	private static final long serialVersionUID = 4507254628220657407L;

	public BlacklistedUserNameException(String message) {
		super(message);
	}
}
