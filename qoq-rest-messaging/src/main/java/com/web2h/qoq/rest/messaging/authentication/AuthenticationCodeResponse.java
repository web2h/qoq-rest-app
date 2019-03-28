package com.web2h.qoq.rest.messaging.authentication;

import com.web2h.qoq.rest.messaging.QoqResponse;

public class AuthenticationCodeResponse extends QoqResponse {

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
