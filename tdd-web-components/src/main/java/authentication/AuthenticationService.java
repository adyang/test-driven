package authentication;

public interface AuthenticationService {
	boolean isValidLogin(String username, String password);
}
