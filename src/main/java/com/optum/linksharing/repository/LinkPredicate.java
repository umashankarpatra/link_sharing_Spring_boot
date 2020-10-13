package com.optum.linksharing.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.optum.linksharing.model.Link;
import com.optum.linksharing.model.Link_;
import com.optum.linksharing.model.Tag_;

public class LinkPredicate {

	public static Predicate[] getLinkPredicates(CriteriaBuilder criteriaBuilder, Root<Link> root, String tag) {
		List<Predicate> predicates = new ArrayList<>();
		if (tag != null) {
			predicates.add(root.get(Tag_.tagName.getName()).in(tag));
			// predicates.add(criteriaBuilder.equal(root.get(Link_.tags.getName()), tag));
		}
		predicates.add(criteriaBuilder.equal(root.get(Link_.active.getName()), true));
		return predicates.toArray(new Predicate[predicates.size()]);

	}

}
