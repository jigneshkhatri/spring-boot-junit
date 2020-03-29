package in.quallit.junitwithmockito.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.quallit.junitwithmockito.entity.User;
import in.quallit.junitwithmockito.exception.CustomException;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerWithMockMVCTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testSave_casePositive() throws Exception {
		mockMvc.perform(post("/user/save").content(asJsonString(new User(0L, "JMK", "Ahm")))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(greaterThan(0))));
	}

	@Test
	public void testSave_caseNegative() throws Exception {
		try {
			mockMvc.perform(post("/user/save").content(asJsonString(new User(0L, null, "Ahm")))
					.contentType(MediaType.APPLICATION_JSON_VALUE));
		} catch (NestedServletException e) {
			if (e.getCause() instanceof CustomException) {
				CustomException ex = (CustomException) e.getCause();
				assertEquals("User name is required", ex.getMessage());
				assertEquals("ERR1", ex.getCode());
			}
		}
	}

	@Test
	public void testFindById_caseFound() throws Exception {
		mockMvc.perform(get("/user/findById/1")).andExpect(status().isFound()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("JMK"));
	}

	@Test
	public void testFindById_caseNotFound() throws Exception {
		mockMvc.perform(get("/user/findById/2")).andExpect(status().isNotFound());
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
