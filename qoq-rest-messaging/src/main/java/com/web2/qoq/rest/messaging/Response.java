package com.web2.qoq.rest.messaging;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Response {

	private List<ErrorResponse> errors;

	@JsonGetter
	public ErrorResponse getError() {
		if (errors != null && errors.size() == 1) {
			return errors.get(0);
		}
		return null;
	}

	public List<ErrorResponse> getErrors() {
		if (errors == null || errors.size() <= 1) {
			return null;
		}
		return errors;
	}
}
