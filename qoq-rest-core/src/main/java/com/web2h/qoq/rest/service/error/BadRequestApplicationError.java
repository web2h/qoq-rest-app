package com.web2h.qoq.rest.service.error;

import org.springframework.http.HttpStatus;

public enum BadRequestApplicationError implements ApplicationError {

	TBD("400-001", "The authentication token has expired");

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
