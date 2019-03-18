package com.web2h.qoq.rest.core.error;

import org.springframework.http.HttpStatus;

public enum BadRequestApplicationError implements ApplicationError {

	EXPIRED_AUTHENTICATION_TOKEN("400-001", "The authentication token has expired"),
	INVALID_AUTHENTICATION_TOKEN("400-002", "The authentication token is invalid");

	private String code;

	private String label;

	private BadRequestApplicationError(String code, String label) {
		this.code = code;
		this.label = label;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
