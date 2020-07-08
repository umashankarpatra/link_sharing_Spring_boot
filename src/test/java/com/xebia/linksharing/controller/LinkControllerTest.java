package com.xebia.linksharing.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.linksharing.model.link.dto.LinkRequest;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LinkControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void mockMvc_should_not_be_null() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	public void testCreateLink() throws Exception {
		LinkRequest request = new LinkRequest();
		request.setUrl("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-release");
		request.setTitle("Snowpack 2.0 Release");
		String[] tags = { "web" };
		request.setTags(tags);
		String json = objectMapper.writeValueAsString(request);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/v1/web-links").contentType(MediaType.APPLICATION_JSON).content(json))
				.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.favCount").value(0)).andExpect(jsonPath("$.title").value("Snowpack 2.0 Release"))
				.andExpect(jsonPath("$.url").value("https://www.snowpack.dev/posts/2020-05-26-snowpack-2-0-release"));

	}
	
	@Test
	public void testGetAllLinks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/web-links").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.favCount").isNotEmpty())
				.andExpect(jsonPath("$.url").isNotEmpty());

	}

	@Test
	public void testUpdateFavLink() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/web-links/1/mark-favourite").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().is4xxClientError());

	}

	@Test
	public void testGetDeactivateLinksExpectIsOk() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/v1/web-links/deactivate-Links").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.active").isNotEmpty());

	}
	
	@Test
	public void testGetDeactivateLinksExpectIsNotFound() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/v1/web-links/deactivate-Links").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotFound());

	}

}
