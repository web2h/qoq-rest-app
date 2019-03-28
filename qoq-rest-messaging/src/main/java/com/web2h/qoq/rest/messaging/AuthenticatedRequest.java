package com.web2h.qoq.rest.messaging;

import javax.validation.constraints.NotNull;

public class AuthenticatedRequest {

	@NotNull
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
