package com.interview.template.exceptions;


import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.interview.template.model.ErrorResponse;

@ControllerAdvice
public class UserException extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = {UserNameExistsException.class})
	public ResponseEntity<Object> handleUserNameExistException(Exception exception) {
		
		String errorDesc = exception.getLocalizedMessage();
		
		if (StringUtils.isBlank(errorDesc)) {
			errorDesc = exception.toString();
		}
		ErrorResponse errorResponse = new ErrorResponse(new Date(), errorDesc);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {UserEmailExistsException.class})
	public ResponseEntity<Object> handleUserEmailExistException(Exception exception) {
		
		String errorDesc = exception.getLocalizedMessage();
		
		if (StringUtils.isBlank(errorDesc)) {
			errorDesc = exception.toString();
		}
		ErrorResponse errorResponse = new ErrorResponse(new Date(), errorDesc);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {UserNotFoundException.class})
	public ResponseEntity<Object> handleUserNotFoundException(Exception exception) {
		
		String errorDesc = exception.getLocalizedMessage();
		
		if (StringUtils.isBlank(errorDesc)) {
			errorDesc = exception.toString();
		}
		ErrorResponse errorResponse = new ErrorResponse(new Date(), errorDesc);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {BlacklistedUserNameException.class})
	public ResponseEntity<Object> handleBlacklistedUserNameException(Exception exception) {
		
		String errorDesc = exception.getLocalizedMessage();
		
		if (StringUtils.isBlank(errorDesc)) {
			errorDesc = exception.toString();
		}
		ErrorResponse errorResponse = new ErrorResponse(new Date(), errorDesc);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
