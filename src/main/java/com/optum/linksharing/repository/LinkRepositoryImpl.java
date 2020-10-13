package com.optum.linksharing.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.optum.linksharing.model.Link;
import com.optum.linksharing.model.Link_;

@Repository
public class LinkRepositoryImpl extends AbstractJPA implements LinkRepositoryJPA {

	@Override
	public List<Link> getDeactivateLink() {
		CriteriaBuilder criteriaBuilder = criteriaBuilder();
		CriteriaQuery<Link> query = criteriaQuery(Link.class);
		Root<Link> rate = query.from(Link.class);
		query.where(criteriaBuilder.equal(rate.get(Link_.active.getName()), false));
		query.orderBy(criteriaBuilder.asc(rate.get(Link_.createdAt.getName())));
		query.select(rate);
		TypedQuery<Link> typedQuery = typedQuery(query);
		List<Link> deactivateLinks = getUnmodifiableResultList(typedQuery);
		return deactivateLinks;

	}

	@Override
	public Page<Link> getAllLinks(final String tag, final Pageable pageable) {

		long totalRecords = findLinksCount(tag);
		if (totalRecords == 0) {
			return new PageImpl<>(Collections.emptyList(), pageable, totalRecords);
		} else {
			CriteriaBuilder criteriaBuilder = criteriaBuilder();
			CriteriaQuery<Link> query = criteriaQuery(Link.class);
			Root<Link> rate = query.from(Link.class);

			if (!StringUtils.isEmpty(tag)) {
				query.where(LinkPredicate.getLinkPredicates(criteriaBuilder, rate, tag));
			} else {
				query.where(criteriaBuilder.equal(rate.get(Link_.active.getName()), true));
			}
			query.select(rate);
			query.distinct(true);
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(criteriaBuilder.desc(rate.get(Link_.favCount.getName())));
			orderList.add(criteriaBuilder.asc(rate.get(Link_.createdAt.getName())));
			query.orderBy(orderList);
			TypedQuery<Link> typedQuery = typedQuery(query);
			typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
			typedQuery.setMaxResults(pageable.getPageSize());
			List<Link> results = getUnmodifiableResultList(typedQuery);
			return new PageImpl<>(results, pageable, totalRecords);
		}

	}

	private long findLinksCount(final String tags) {
		CriteriaBuilder criteriaBuilder = criteriaBuilder();
		CriteriaQuery<Long> query = criteriaQuery(Long.class);
		Root<Link> rate = query.from(Link.class);
		/*
		 * if (!StringUtils.isEmpty(artist)) {
		 * query.where(criteriaBuilder.equal(rate.get(Album_.artist.getName()),
		 * artist)); }
		 */
		query.select(criteriaBuilder.countDistinct(rate));
		return typedQuery(query).getSingleResult().longValue();
	}

}
