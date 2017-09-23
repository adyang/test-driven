package servlet;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import authentication.AuthenticationService;
import authentication.FakeAuthenticationService;
import parameters.LoginParam;

public class TestLoginServlet {
	private static final String CORRECT_PASSWORD = "correctpassword";
	private static final String VALID_USERNAME = "validuser";
	private HttpServlet servlet;
	private FakeAuthenticationService authenticator;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void setUp() {
		authenticator = new FakeAuthenticationService();
		authenticator.addUser(VALID_USERNAME, CORRECT_PASSWORD);

		servlet = new LoginServlet() {
			@Override
			protected AuthenticationService getAuthenticationService() {
				return authenticator;
			}
		};

		request = new MockHttpServletRequest("GET", "/login");
		response = new MockHttpServletResponse();
	}

	@Test
	public void wrongPasswordShouldRedirectToErrorPage() throws ServletException, IOException {
		request.addParameter(LoginParam.USERNAME.paramName(), VALID_USERNAME);
		request.addParameter(LoginParam.PASSWORD.paramName(), "wrongpassword");
		servlet.service(request, response);
		assertEquals("/invalidlogin", response.getRedirectedUrl());
	}

	@Test
	public void validLoginForwardsToFrontPageAndStoresUsername() throws ServletException, IOException {
		request.addParameter(LoginParam.USERNAME.paramName(), VALID_USERNAME);
		request.addParameter(LoginParam.PASSWORD.paramName(), CORRECT_PASSWORD);
		servlet.service(request, response);
		assertEquals("/frontpage", response.getRedirectedUrl());
		assertEquals(VALID_USERNAME, request.getSession().getAttribute("username"));
	}
}
