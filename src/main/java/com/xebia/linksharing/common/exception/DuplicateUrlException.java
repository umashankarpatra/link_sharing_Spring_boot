package com.xebia.linksharing.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateUrlException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateUrlException(String message) {
		super(message);
	}

}
