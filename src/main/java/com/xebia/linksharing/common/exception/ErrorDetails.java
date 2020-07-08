package com.xebia.linksharing.common.exception;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private List<String> details;
}