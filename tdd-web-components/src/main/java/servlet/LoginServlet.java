package servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String user = request.getParameter("j_username");
		String pass = request.getParameter("j_password");
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
