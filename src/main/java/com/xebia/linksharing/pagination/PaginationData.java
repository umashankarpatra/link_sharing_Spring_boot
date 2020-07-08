package com.xebia.linksharing.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "paginationData", description = "Page data to support pagination")
public class PaginationData extends RepresentationModel<PaginationData> {

	@ApiModelProperty(name = "currentPage", value = "Current page number", required = true, example = "3", position = 1)
	@Setter(value = AccessLevel.NONE)
	private int currentPage;

	@ApiModelProperty(name = "pageSize", value = "Page size, number of records per page", required = true, example = "16", position = 2)
	@Setter(value = AccessLevel.NONE)
	private int pageSize;

	@Setter(value = AccessLevel.NONE)
	@JsonIgnore
	private Sort sort;

	@ApiModelProperty(name = "totalPages", value = "Total number of pages available, matching given filters", required = true, example = "35", position = 3)
	@Setter(value = AccessLevel.NONE)
	private int totalPages;

	@ApiModelProperty(name = "totalRecords", value = "Total number of records available, matching given filters", required = true, example = "145", position = 4)
	@Setter(value = AccessLevel.NONE)
	private long totalRecords;

	private PaginationData(final int currentPage, final int pageSize, final long totalRecords, final Sort sort) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
		this.totalPages = this.pageSize == 0 ? 1 : (int) Math.ceil((double) this.totalRecords / (double) this.pageSize);
		this.sort = sort;
	}

	public static PaginationData of(final Pageable pageable, final long totalRecords) {
		return new PaginationData(pageable.getPageNumber(), pageable.getPageSize(), totalRecords, pageable.getSort());
	}

	@ApiModelProperty(name = "isFirst", value = "Is this page first", required = true, example = "true", position = 5)
	@JsonProperty("isFirst")
	public boolean isFirst() {
		return !hasPrevious();
	}

	@ApiModelProperty(name = "isLast", value = "Is this page last", required = true, example = "false", position = 6)
	@JsonProperty("isLast")
	public boolean isLast() {
		return !hasNext();
	}

	@ApiModelProperty(name = "hasNext", value = "Does next page exists", required = true, example = "true", position = 7)
	@JsonProperty("hasNext")
	public boolean hasNext() {
		return getCurrentPage() + 1 < getTotalPages();
	}

	@ApiModelProperty(name = "hasPrevious", value = "Does previous page exists", required = true, example = "false", position = 8)
	@JsonProperty("hasPrevious")
	public boolean hasPrevious() {
		return getCurrentPage() > 0;
	}

	public int queryFirstResult() {
		return this.getCurrentPage() * this.getPageSize();
	}

	public int queryMaxResults() {
		return this.getPageSize();
	}

	@ApiModelProperty(name = "header", value = "Text description of this page", required = true, example = "Page 0 of 12", position = 9)
	@JsonProperty("header")
	@Override
	public String toString() {
		return String.format("Page %s of %d", getCurrentPage() + 1, getTotalPages());
	}
}
