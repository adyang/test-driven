package servlet;

public enum LoginParam {
	USERNAME("j_username"), PASSWORD("j_password");

	private String paramName;

	LoginParam(String paramName) {
		this.paramName = paramName;
	}

	public String paramName() {
		return paramName;
	}
}
