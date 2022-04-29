package io.github.juniq.apicall.http;

public class Response {
	private String gender;
	private String name;
	private int id;
	private String email;
	private String status;

	public String getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Response{" +
			"gender='" + gender + '\'' +
			", name='" + name + '\'' +
			", id=" + id +
			", email='" + email + '\'' +
			", status='" + status + '\'' +
			'}';
	}
}