package com.web2h.qoq.rest.core.error;

import org.springframework.http.HttpStatus;

public interface ApplicationError {

	HttpStatus getHttpStatus();

	String getCode();

	String getLabel();
}
