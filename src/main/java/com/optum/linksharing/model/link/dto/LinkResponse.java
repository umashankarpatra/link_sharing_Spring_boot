package com.optum.linksharing.model.link.dto;

import java.io.Serializable;
import java.util.Set;

import com.optum.linksharing.model.Link;
import com.optum.linksharing.model.Tag;

import lombok.Data;

@Data
public class LinkResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String url;

	private String title;

	private Integer favCount;

	private String[] tags;

	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getFavCount() {
		return favCount;
	}

	public void setFavCount(Integer favCount) {
		this.favCount = favCount;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static LinkResponse of(final Link link) {

		LinkResponse response = new LinkResponse();
		response.setId(link.getId());
		response.setTitle(link.getTitle());
		response.setFavCount(link.getFavCount());
		response.setUrl(link.getUrl());
		response.setActive(link.getActive());

		String[] tags = new String[link.getTags().size()];
		Set<Tag> set = link.getTags();
		int i = 0;
		for (Tag tag : set) {
			tags[i++] = tag.getTagName();
		}

		response.setTags(tags);
		return response;

	}

}
