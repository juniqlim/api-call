package io.github.juniqlim.apicall.http;

public class Request {
    private final String gender;
    private final String name;
    private final String email;
    private final String status;

	private Request(String gender, String name, String email, String status) {
		this.gender = gender;
		this.name = name;
		this.email = email;
		this.status = status;
	}

	public static Request of(String gender, String name, String email, String status) {
		return new Request(gender, name, email, status);
	}

	public String getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getStatus() {
		return status;
	}
}
