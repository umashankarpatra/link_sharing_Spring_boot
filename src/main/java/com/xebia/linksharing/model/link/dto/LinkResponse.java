package com.xebia.linksharing.model.link.dto;

import java.io.Serializable;
import java.util.Set;

import com.xebia.linksharing.model.Link;
import com.xebia.linksharing.model.Tag;

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
