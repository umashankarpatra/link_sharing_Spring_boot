package com.xebia.linksharing.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WebLinkNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public WebLinkNotFoundException(String message) {
		super(message);
	}
}
