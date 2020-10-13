package com.optum.linksharing.common.exception;

import java.util.Date;
import java.util.List;

public class ErrorDetails {

	public ErrorDetails() {

	}

	private Date timestamp;
	private String message;
	private List<String> details;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public static ErrorDetails of(Date timestamp, String message, List<String> details) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.timestamp = timestamp;
		errorDetails.message = message;
		errorDetails.details = details;
		return errorDetails;

	}
}