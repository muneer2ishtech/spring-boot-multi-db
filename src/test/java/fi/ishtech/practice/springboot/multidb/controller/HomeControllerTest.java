package fi.ishtech.practice.springboot.multidb.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import fi.ishtech.practice.springboot.multidb.SpringBootMultiDbApplication;

/**
 *
 * @author Muneer Ahmed Syed
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringBootMultiDbApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomeControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@Order(1)
	public void testIndexOk() throws Exception {
		mvc.perform(get("/"))
			.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void testAboutOk() throws Exception {
		mvc.perform(get("/about"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.applicationName").exists())
			.andExpect(jsonPath("$.dbProductName").exists());
	}

}