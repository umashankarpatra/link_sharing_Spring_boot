package com.optum.linksharing.service;

import static com.optum.linksharing.common.util.ApplicationConstants.ONE;
import static com.optum.linksharing.common.util.ExceptionConstants.DUPLICATE_URL_FOUND;
import static com.optum.linksharing.common.util.ExceptionConstants.INVALID_URL_FOUND;
import static com.optum.linksharing.common.util.ExceptionConstants.WEB_LINK_NOT_FOUND;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.optum.linksharing.LinkSharing;
import com.optum.linksharing.common.exception.DuplicateUrlException;
import com.optum.linksharing.common.exception.InvalidUrlException;
import com.optum.linksharing.common.exception.WebLinkNotFoundException;
import com.optum.linksharing.model.Link;
import com.optum.linksharing.repository.LinkRepository;

@Service
public class LinkServiceImpl implements LinkService {

	@Autowired
	private LinkRepository linkRepository;

	private Logger log = LoggerFactory.getLogger(LinkSharing.class);

	@Override
	@Transactional
	public Link submitWebLink(final Link link) {

		Link createdLink = null;
		String url = link.getUrl();
		if (validateURL(url)) {
			Link exitUrl = this.linkRepository.findByUrl(url);
			if (exitUrl != null) {
				throw new DuplicateUrlException(DUPLICATE_URL_FOUND + url);
			}
			createdLink = this.linkRepository.save(link);
			this.linkRepository.flush();
		} else {
			throw new InvalidUrlException(INVALID_URL_FOUND + url);
		}
		return createdLink;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Link> getDeactivateLink() {
		return this.linkRepository.getDeactivateLink();

	}

	@Override
	@Transactional
	public boolean updateFavLink(final Long id) {
		Optional<Link> link = this.linkRepository.findById(id);
		if (!link.isPresent()) {
			throw new WebLinkNotFoundException(WEB_LINK_NOT_FOUND + id);
		}
		if (link.get().getFavCount() == null) {
			return false;

		}
		link.get().update(link.get().getFavCount() + ONE);
		this.linkRepository.flush();
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Link> getAllLinks(final String tag, final Pageable pageable) {
		return this.linkRepository.getAllLinks(tag, pageable);

	}

	private boolean validateURL(String url) {

		try {
			URL urlObj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if ((responseCode > 400) || (responseCode > 500)) {
				//throw new InvalidUrlException(INVALID_URL_FOUND + url);
			}

		} catch (Exception e) {
			log.error("exception in connecting URL : {} \n {} ", url, e.getMessage());
			return false;
		}
		return true;
	}

	@Transactional
	public void deactiveLinksTask(Date date) {
		List<Link> links = this.linkRepository.findRecordByActiveAndCreatedAtBefore(true, date);
		if (links != null) {
			links.forEach(link -> link.setActive(false));
		}
		log.info("no of records : {}", links.size());
		this.linkRepository.flush();
	}

}