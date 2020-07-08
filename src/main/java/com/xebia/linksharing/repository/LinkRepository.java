package com.xebia.linksharing.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xebia.linksharing.model.Link;

public interface LinkRepository extends JpaRepository<Link, Long>, LinkRepositoryJPA {

	public Link findByUrl(String url);

	public List<Link> findRecordByActiveAndCreatedAtBefore(Boolean active, Date createdAt);

}
