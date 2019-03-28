package com.web2h.qoq.rest.messaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.web2h.qoq.rest.messaging.error.ErrorCode;
import com.web2h.qoq.rest.messaging.error.ErrorResponse;
import com.web2h.qoq.rest.messaging.error.Field;
import com.web2h.qoq.rest.service.error.ApplicationError;

public class QoqResponse {

	private List<ErrorResponse> errors = new ArrayList<>();

	public void addError(ApplicationError error) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(error.getCode());
		errorResponse.setMessage(error.getLabel());
		errors.add(errorResponse);
	}

	public void addValidationError(FieldError springFieldError) {

		ErrorResponse errorResponse = new ErrorResponse();

		String fieldName = springFieldError.getField().substring(springFieldError.getField().lastIndexOf(".") + 1);
		errorResponse.setField(Field.getByLowerCaseValue(fieldName));

		if (springFieldError.getRejectedValue() instanceof Date) {
			// TODO
		} else if (springFieldError.getRejectedValue() != null) {
			errorResponse.setOriginalValue(springFieldError.getRejectedValue().toString());
		}

		String code = springFieldError.getCodes()[springFieldError.getCodes().length - 1];
		if ("Size".equals(code)) {
			int minLength = Math.min((Integer) springFieldError.getArguments()[1],
					(Integer) springFieldError.getArguments()[2]);
			int maxLength = Math.max((Integer) springFieldError.getArguments()[1],
					(Integer) springFieldError.getArguments()[2]);
			if (((String) springFieldError.getRejectedValue()).length() > maxLength) {
				code = ErrorCode.TOO_LONG.getJsonValue();
				errorResponse.setMessage(String.valueOf(maxLength));
			} else {
				code = ErrorCode.TOO_SHORT.getJsonValue();
				errorResponse.setMessage(String.valueOf(minLength));
			}
		}
		errorResponse.setCode(ErrorCode.getBySpringFieldError(code).getJsonValue());
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
