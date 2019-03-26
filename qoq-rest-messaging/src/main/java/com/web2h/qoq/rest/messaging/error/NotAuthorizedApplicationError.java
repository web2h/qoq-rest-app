package com.web2h.qoq.rest.messaging.error;

import org.springframework.http.HttpStatus;

public enum NotAuthorizedApplicationError implements ApplicationError {

	MISSING_AUTHENTICATION_TOKEN("401-001", "The authentication token is missing"),
	EXPIRED_AUTHENTICATION_TOKEN("401-002", "The authentication token has expired"),
	INVALID_AUTHENTICATION_TOKEN("401-003", "The authentication token is invalid"),
	DIFFERENT_USER_IN_AUTHENTICATION_TOKEN("401-004", "The user in the token is not the right one"),
	USER_NOT_FOUND_BY_AUTHENTICATION_TOKEN("401-005", "No user could be found with this authentication token"),
	DIFFERENT_USER_LINKED_TO_CODE("401-006", "The user linked to the code is not the provided one"),
	AUTHENTICATION_CODE_IS_EXPIRED("401-007", "The authentication code is expired");

	private String code;

	private String label;

	private NotAuthorizedApplicationError(String code, String label) {
		this.code = code;
		this.label = label;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNAUTHORIZED;
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