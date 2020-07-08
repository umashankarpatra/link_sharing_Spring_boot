package com.xebia.linksharing.model.link.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LinkRequest {

	@NotNull
	@NotEmpty(message = "URL is manadatory")
	private String url;
	@NotNull
	@NotEmpty(message = "Title is manadatory")
	private String title;
	@NotNull
	@NotEmpty(message = "One tag is manadatory")
	private String[] tags;

}
