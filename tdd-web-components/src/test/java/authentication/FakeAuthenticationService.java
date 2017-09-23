package authentication;

import java.util.HashMap;
import java.util.Map;

import authentication.AuthenticationService;

public class FakeAuthenticationService implements AuthenticationService {
	private Map<String, String> users = new HashMap<String, String>();

	@Override
	public boolean isValidLogin(String user, String pass) {
		return users.containsKey(user) && pass.equals(users.get(user));
	}

	public void addUser(String user, String pass) {
		users.put(user, pass);
	}
}
