package com.optum.linksharing.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.optum.linksharing.model.Link;

public interface LinkService {

	public Link submitWebLink(final Link link);

	public List<Link> getDeactivateLink();

	public boolean updateFavLink(final Long id);

	public Page<Link> getAllLinks(final String tag, final Pageable pageable);

	public void deactiveLinksTask(Date date);

}