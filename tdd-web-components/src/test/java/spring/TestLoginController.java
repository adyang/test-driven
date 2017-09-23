package spring;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import authentication.FakeAuthenticationService;
import parameters.LoginParam;

public class TestLoginController {
	private static final String VALID_USERNAME = "validuser";
	private static final String CORRECT_PASSWORD = "correctpassword";
	private FakeAuthenticationService mock;
	private Controller controller;
	private MockHttpServletResponse response;
	private MockHttpServletRequest request;

	@Before
	public void setUp() {
		mock = new FakeAuthenticationService();
		mock.addUser(VALID_USERNAME, CORRECT_PASSWORD);
		controller = new LoginController(mock);

		request = new MockHttpServletRequest();
		request.setMethod(HttpMethod.GET.toString());
		response = new MockHttpServletResponse();
	}

	@Test
	public void wrongPasswordShouldRedirectToErrorPage() throws Exception {
		request.addParameter(LoginParam.USERNAME.paramName(), VALID_USERNAME);
		request.addParameter(LoginParam.PASSWORD.paramName(), "nosuchpassword");
		ModelAndView v = controller.handleRequest(request, response);
		assertEquals("wrongpassword", v.getViewName());
	}

	@Test
	public void validLoginForwardsToFrontPage() throws Exception {
		request.addParameter(LoginParam.USERNAME.paramName(), VALID_USERNAME);
		request.addParameter(LoginParam.PASSWORD.paramName(), CORRECT_PASSWORD);
		ModelAndView v = controller.handleRequest(request, response);
		assertEquals("frontpage", v.getViewName());
	}
}
