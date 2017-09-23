package servlet;

public interface AuthenticationService {
	boolean isValidLogin(String username, String password);
}
