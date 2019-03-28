package com.web2h.qoq.rest.messaging.authentication;

import javax.validation.constraints.NotNull;

import com.web2h.qoq.rest.messaging.QoqRequest;
import com.web2h.tools.string.StringTools;

public class AuthenticationCodeConfirmationRequest extends QoqRequest {

	@NotNull
	private String email;

	@NotNull
	private String code;

	@Override
	public String toString() {
		return StringTools.format("Authentication code request [email={}] [code={}", email, code);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}