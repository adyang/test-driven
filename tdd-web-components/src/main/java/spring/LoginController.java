package spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import authentication.AuthenticationService;
import parameters.LoginParam;

public class LoginController implements Controller {
	private AuthenticationService authenticator;

	public LoginController(AuthenticationService authService) {
		this.authenticator = authService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String user = request.getParameter(LoginParam.USERNAME.paramName());
		String pass = request.getParameter(LoginParam.PASSWORD.paramName());
		if (authenticator.isValidLogin(user, pass)) {
			return new ModelAndView("frontpage");
		} else {
			return new ModelAndView("wrongpassword");
		}
	}
}
