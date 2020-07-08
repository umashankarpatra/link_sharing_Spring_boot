package com.xebia.linksharing.controller;

import static com.xebia.linksharing.common.util.HeaderUtil.addError;
import static com.xebia.linksharing.common.util.HeaderUtil.addSuccess;
import static com.xebia.linksharing.common.util.RateMessageConstants.DEACTIVATE_LINK_NOT_AVALIABLE;
import static com.xebia.linksharing.common.util.RateMessageConstants.LINK_COUNT_UPDATED_SUCCESSFULLY;
import static com.xebia.linksharing.common.util.RateMessageConstants.LINK_COUNT_UPDATE_FAILED;
import static com.xebia.linksharing.common.util.RateMessageConstants.RECORD__NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.linksharing.common.util.ImmutableCollectors;
import com.xebia.linksharing.model.Link;
import com.xebia.linksharing.model.link.dto.LinkRequest;
import com.xebia.linksharing.model.link.dto.LinkResponse;
import com.xebia.linksharing.pagination.PaginatedResource;
import com.xebia.linksharing.pagination.PaginatedResourceAssembler;
import com.xebia.linksharing.service.LinkService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/web-links")
public class LinkSharingController {

	@Autowired
	private LinkService linkService;

	@Autowired
	private PaginatedResourceAssembler<Link> pagedResourcesAssembler;

	@PostMapping()
	public ResponseEntity<LinkResponse> submitWebLink(
			@RequestBody @Valid @NotEmpty final @NotNull LinkRequest linkRequest) {
		Link webLink = this.linkService.submitWebLink(Link.of(linkRequest));
		LinkResponse linkResponse = LinkResponse.of(webLink);
		return new ResponseEntity<>(linkResponse, HttpStatus.CREATED);

	}

	@GetMapping()
	@ApiOperation(nickname = "get-web-link-page", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "Gets a page of Links matching the selection filters and sort criteria", notes = "", response = PaginatedResource.class)
	public ResponseEntity<PaginatedResource<Link>> getAllLinks(
			@ApiParam(name = "tag", value = "web,nodejs", example = "web") @RequestParam(value = "tag", required = false) final String tag,
			@ApiIgnore @PageableDefault(page = 0, size = 15) final Pageable pageable) {

		Page<Link> results = this.linkService.getAllLinks(tag, pageable);
		if (results.isEmpty()) {
			return ResponseEntity.notFound().headers(addError(RECORD__NOT_FOUND)).build();

		}
		return ResponseEntity.ok(this.pagedResourcesAssembler.assemble(results));

	}

	@PutMapping("/{id}/mark-favourite")
	public ResponseEntity<Void> updateFavLink(
			@ApiParam(value = "Weblink id", example = "11", required = true) @PathVariable(name = "id", required = true) @Valid @NotEmpty final Long id) {

		boolean updated = this.linkService.updateFavLink(id);
		return ResponseEntity.ok()
				.headers(updated ? addSuccess(LINK_COUNT_UPDATED_SUCCESSFULLY) : addError(LINK_COUNT_UPDATE_FAILED))
				.build();

	}

	@GetMapping("/deactivate-Links")
	public ResponseEntity<List<LinkResponse>> getDeactivatedLink() {

		List<LinkResponse> response = this.linkService.getDeactivateLink().stream().map(LinkResponse::of)
				.collect(ImmutableCollectors.toImmutableList());

		if (response.isEmpty()) {
			return ResponseEntity.notFound().headers(addSuccess(DEACTIVATE_LINK_NOT_AVALIABLE)).build();
		}
		return ResponseEntity.ok(response);

	}

}
