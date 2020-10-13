package com.optum.linksharing.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.optum.linksharing.common.exception.DuplicateUrlException;
import com.optum.linksharing.common.exception.InvalidUrlException;
import com.optum.linksharing.common.exception.WebLinkNotFoundException;
import com.optum.linksharing.model.Link;
import com.optum.linksharing.model.Tag;
import com.optum.linksharing.repository.LinkRepository;
import com.optum.linksharing.service.LinkServiceImpl;

@RunWith(PowerMockRunner.class)
@SpringBootTest
public class LinkServiceTest {

	@InjectMocks
	private LinkServiceImpl linkService;

	@Mock
	private LinkRepository linkRepository;

	@Rule
	private ExpectedException expectedExceptionRule = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdateResourceNotFoundException() {
		expectedExceptionRule.expect(WebLinkNotFoundException.class);

		Link link = new Link();
		link.setId((long) 1);
		link.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-release");
		link.setTitle("Snowpack 2.0 Release");
		link.setFavCount(1);
		link.setActive(true);
		Set<Tag> tags = new HashSet<Tag>();
		Tag tag = new Tag();
		tag.setTagName("web");
		tags.add(tag);
		link.setTags(tags);
		link.setCreatedAt(new Date());

		when(linkRepository.findById((long) 1)).thenReturn(Optional.empty());
		linkService.updateFavLink((long) 1);

	}

	@Test
	public void testUpdateFavLinkReturnsFalse() {

		Link link = new Link();
		link.setId((long) 1);
		link.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-release");
		link.setTitle("Snowpack 2.0 Release");
		link.setFavCount(null);
		Set<Tag> tags = new HashSet<Tag>();
		Tag tag = new Tag();
		tag.setTagName("web");
		tags.add(tag);
		link.setTags(tags);
		link.setActive(true);
		link.setCreatedAt(new Date());

		when(linkRepository.findById((long) 1)).thenReturn(Optional.of(link));
		assertFalse(linkService.updateFavLink((long) 1));

	}

	@Test
	public void testUpdateFavLinkReturnsTrue() {

		Link link = new Link();
		link.setId((long) 1);
		link.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-release");
		link.setTitle("Snowpack 2.0 Release");
		link.setFavCount(0);
		Set<Tag> tags = new HashSet<Tag>();
		Tag tag = new Tag();
		tag.setTagName("web");
		tags.add(tag);
		link.setTags(tags);
		link.setActive(true);
		link.setCreatedAt(new Date());

		when(this.linkRepository.findById((long) 1)).thenReturn(Optional.of(link));
		assertTrue(this.linkService.updateFavLink((long) 1));

	}

	@Test
	public void testGetDeactivateLink() {

		Link link = new Link();
		link.setId((long) 1);
		link.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-release");
		link.setTitle("Snowpack 2.0 Release");
		link.setFavCount(0);
		Set<Tag> tags = new HashSet<Tag>();
		Tag tag = new Tag();
		tag.setTagName("web");
		tags.add(tag);
		link.setTags(tags);
		link.setActive(false);
		link.setCreatedAt(new Date());

		List<Link> deactivateLink = Arrays.asList(link);

		when(linkRepository.getDeactivateLink()).thenReturn(deactivateLink);
		assertEquals(this.linkService.getDeactivateLink(), deactivateLink);

	}

	@Test
	public void testGetAllLink() {

		Link link = new Link();
		link.setId((long) 1);
		link.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-release");
		link.setTitle("Snowpack 2.0 Release");
		link.setFavCount(0);
		Set<Tag> tags = new HashSet<Tag>();
		Tag tag = new Tag();
		tag.setTagName("web");
		tags.add(tag);
		link.setTags(tags);
		link.setActive(true);
		link.setCreatedAt(new Date());
		PageRequest page = PageRequest.of(1, 12);

		List<Link> activateLink = Arrays.asList(link);
		PageImpl<Link> pageImpl = new PageImpl<Link>(activateLink, page, 1);

		when(linkRepository.getAllLinks(null, page)).thenReturn(pageImpl);
		assertTrue(this.linkService.getAllLinks(null, page).getSize() > 0);
	}

	@Test
	public void testSubmitWebLinkThrowDuplicateUrlException() {
		expectedExceptionRule.expect(DuplicateUrlException.class);

		Link link = new Link();
		link.setId((long) 1);
		link.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-release");
		link.setTitle("Snowpack 2.0 Release");
		link.setFavCount(0);
		link.setActive(true);
		Set<Tag> tags = new HashSet<Tag>();
		Tag tag = new Tag();
		tag.setTagName("web");
		tags.add(tag);
		link.setTags(tags);
		link.setCreatedAt(new Date());

		when(linkRepository.findByUrl(link.getUrl())).thenReturn(link);
		this.linkService.submitWebLink(link);

	}

	@Test
	public void testSubmitWebLink() {

		Link link = new Link();
		link.setId((long) 1);
		link.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-release");
		link.setTitle("Snowpack 2.0 Release");
		link.setFavCount(0);
		link.setActive(true);
		link.setCreatedAt(new Date());

		when(this.linkRepository.save(link)).thenReturn(link);
		assertEquals(this.linkService.submitWebLink(link), link);

	}
	
	@Test
	public void testSubmitWebLinkInvalidUrlFound() {
		expectedExceptionRule.expect(InvalidUrlException.class);

		Link link = new Link();
		link.setId((long) 1);
		link.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-rel");
		link.setTitle("Snowpack 2.0 Release");
		link.setFavCount(0);
		link.setActive(true);
		link.setCreatedAt(new Date());

		when(this.linkRepository.save(link)).thenReturn(link);
		assertEquals(this.linkService.submitWebLink(link), link);

	}
	
	@Test
	public void testDeactiveLinksTask() {
		Link link = new Link();
		link.setId((long) 1);
		link.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-rel");
		link.setTitle("Snowpack 2.0 Release");
		link.setFavCount(0);
		link.setActive(true);
		link.setCreatedAt(new Date());
		
		List<Link> links=Arrays.asList(link);	
		when(this.linkRepository.findRecordByActiveAndCreatedAtBefore(true, new Date())).thenReturn(links);
		this.linkService.deactiveLinksTask(new Date());
		verify(linkRepository).flush();
		/*
		 * System.out.println(links.get(0).getActive());
		 * assertFalse(links.get(0).getActive());
		 */
	}
}
