package velocity;

import org.junit.Test;

import parameters.LoginParam;

public class TestLoginTemplate extends VelocityTestCase {
	@Override
	protected String getWebRoot() {
		return "/velocity";
	}

	@Test
	public void formFieldsArePresent() throws Exception {
		render("/login.vtl");
		assertFormFieldPresent(LoginParam.USERNAME.paramName());
		assertFormFieldPresent(LoginParam.PASSWORD.paramName());
		assertFormSubmitButtonPresent("login");
	}

	@Test
	public void previousUsernameIsRetained() throws Exception {
		String previousUsername = "Bob";
		setAttribute(LoginParam.USERNAME.paramName(), previousUsername);
		render("/login.vtl");
		assertFormFieldValue(LoginParam.USERNAME.paramName(), previousUsername);
	}
}
