package com.web2h.qoq.rest.messaging.authentication;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.web2h.qoq.rest.messaging.QoqRequest;
import com.web2h.tools.string.StringTools;

public class AuthenticationCodeRequest extends QoqRequest {

	@NotNull
	@Email
	private String email;

	@Override
	public String toString() {
		return StringTools.format("Authentication code request [email={}]", email);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}