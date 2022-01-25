package com.accenture.catalogue.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@ExceptionHandler(NoDataException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage handleNoDataException(NoDataException ex) {
		logger.error(ex.getMessage());
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		
		return errorMessage;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleException(Exception ex) {
		logger.error(ex.getMessage());
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				"An error occured. Please contact administrator");
		
		return errorMessage;
	}
	
		
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(ex.getMessage());
		StringBuilder message = new StringBuilder();
		BindingResult bindingResult = ex.getBindingResult();
		for(ObjectError error : bindingResult.getAllErrors()) {
			message.append(" "+ error.getDefaultMessage() + ".");
		}
		
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), message.toString());
		
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

}
