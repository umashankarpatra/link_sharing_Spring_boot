package com.optum.linksharing.model.link.dto;

import java.util.Arrays;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "LinkRequest [url=" + url + ", title=" + title + ", tags=" + Arrays.toString(tags) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(tags);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkRequest other = (LinkRequest) obj;
		if (!Arrays.equals(tags, other.tags))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
