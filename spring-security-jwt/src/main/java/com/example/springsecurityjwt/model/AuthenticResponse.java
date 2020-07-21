package com.example.springsecurityjwt.model;

public class AuthenticResponse {

	private final String jwt;

	public AuthenticResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

}
