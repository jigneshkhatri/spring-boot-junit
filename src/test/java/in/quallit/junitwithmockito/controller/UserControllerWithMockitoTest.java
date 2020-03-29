package in.quallit.junitwithmockito.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import in.quallit.junitwithmockito.entity.User;
import in.quallit.junitwithmockito.exception.CustomException;
import in.quallit.junitwithmockito.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerWithMockitoTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Rule
	private ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setup() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Test
	public void testSavePositive() {
		User user = new User(0L, "JMK", "Ahm");
		Mockito.when(userService.save(user)).thenReturn(user);

		ResponseEntity<User> result = this.userController.save(user);
		assertEquals(result.getStatusCode(), HttpStatus.CREATED);
		assertEquals(result.getBody(), user);
	}

	@Test
	public void testSaveNegative() {
		expectedException.expect(CustomException.class);
		expectedException.expect(Matchers.hasProperty("message", CoreMatchers.is("User name is required")));
		expectedException.expect(Matchers.hasProperty("code", CoreMatchers.is("ERR1")));

		User user = new User(0L, null, "Ahm");

		this.userController.save(user);
	}

	@Test
	public void testFindByIdFound() {
		User user = new User(1L, "JMK", "Ahm");
		Mockito.when(userService.findById(1L)).thenReturn(user);
		ResponseEntity<User> result = this.userController.findById(1L);
		assertEquals(result.getStatusCode(), HttpStatus.FOUND);
		assertEquals(result.getBody(), user);
	}

	@Test
	public void testFindByIdNotFound() {
		Mockito.when(userService.findById(1L)).thenReturn(null);
		ResponseEntity<User> result = this.userController.findById(1L);
		assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
	}

}
