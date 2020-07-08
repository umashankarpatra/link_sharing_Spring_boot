package com.xebia.linksharing.common.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidUrlException.class)
	public ResponseEntity<ErrorDetails> handleInvalidUrlException(InvalidUrlException e) {
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorDetails errorDetails = ErrorDetails.of(new Date(), e.getMessage(), details);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(WebLinkNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleWebLinkNotFoundException(WebLinkNotFoundException e) {
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorDetails errorDetails = ErrorDetails.of(new Date(), e.getMessage(), details);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateUrlException.class)
	public ResponseEntity<ErrorDetails> handleDuplicateUrlException(DuplicateUrlException e) {
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorDetails errorDetails = ErrorDetails.of(new Date(), e.getMessage(), details);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		ErrorDetails errorDetails = ErrorDetails.of(new Date(), "Validation failed!", details);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

}
