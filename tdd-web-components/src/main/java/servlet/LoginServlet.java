package servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import authentication.AuthenticationService;
import parameters.LoginParam;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -584378267922974585L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String user = request.getParameter(LoginParam.USERNAME.paramName());
		String pass = request.getParameter(LoginParam.PASSWORD.paramName());
		if (getAuthenticationService().isValidLogin(user, pass)) {
			response.sendRedirect("/frontpage");
			request.getSession().setAttribute("username", user);
		} else {
			response.sendRedirect("/invalidlogin");
		}
	}

	protected AuthenticationService getAuthenticationService() {
		return null;
	}
}
