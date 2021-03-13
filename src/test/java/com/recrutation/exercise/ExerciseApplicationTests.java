package com.recrutation.exercise;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recrutation.exercise.model.PostData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExerciseApplicationTests {
	@Autowired
	WebApplicationContext webApplicationContext;
	MockMvc mvc;
	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
	@Test
	@Order(1)
	public void downloadData() throws Exception {
		String uri = "/api/download";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	@Order(2)
	public void getDataByUserId() throws Exception {
		String uri = "/api/displayPostsByUser/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		PostData[] postDataList = mapFromJson(content, PostData[].class);
		assertTrue(postDataList.length > 0);
	}
	@Test
	@Order(3)
	public void deletePost() throws Exception {
		String uri = "/api/delete/2";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	@Order(4)
	public void editPost() throws Exception {
		String uri = "/api/edit/4";
		PostData postData = new PostData();
		postData.setTitle("titleTest");
		postData.setBody("bodyTest");
		String postDataJson = mapToJson(postData);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(postDataJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
}
