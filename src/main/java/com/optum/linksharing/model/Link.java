package com.optum.linksharing.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.optum.linksharing.model.link.dto.LinkRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "LINK")
public class Link extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "URL")
	private String url;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "FAV_COUNT")
	private Integer favCount;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "TAG_LINK")
	private Set<Tag> tags;

	@Column(name = "STATUS")
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

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public static Link of(@Valid @NotEmpty @NotNull LinkRequest linkRequest) {
		Link link = new Link();
		link.setUrl(linkRequest.getUrl());
		link.setTitle(linkRequest.getTitle());
		Set<Tag> set = new HashSet<>();
		for (String tag : linkRequest.getTags()) {
			Tag tags = new Tag();
			tags.setTagName(tag);
			set.add(tags);
		}
		link.setTags(set);
		link.setActive(true);
		link.setFavCount(0);
		return link;
	}

	public void update(Integer favCount) {
		this.favCount = favCount;

	}

}
