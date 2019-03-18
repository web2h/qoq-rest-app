package com.web2h.qoq.rest.core.error;

import org.springframework.http.HttpStatus;

public enum NotFoundApplicationError implements ApplicationError {

	USER_NOT_FOUND_BY_AUTHENTICATION_TOKEN("404-001", "No user could be found with this authentication token"),
	USER_NOT_FOUND_BY_EMAIL("404-002", "No user could be found with this email address"),
	AUTHENTICATION_CODE_NOT_FOUND("404-003", "No authentication code exists with that value");

	private String code;

	private String label;

	private NotFoundApplicationError(String code, String label) {
		this.code = code;
		this.label = label;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
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
