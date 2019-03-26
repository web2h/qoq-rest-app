package com.web2.qoq.rest.messaging;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.web2h.qoq.rest.messaging.error.ApplicationError;

public class Response {

	private List<ErrorResponse> errors = new ArrayList<>();

	public void addError(ApplicationError error) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(error.getCode());
		errorResponse.setMessage(error.getLabel());
		errors.add(errorResponse);
	}

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
