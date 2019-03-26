package com.web2h.qoq.rest.messaging.error;

import org.springframework.http.HttpStatus;

public interface ApplicationError {

	HttpStatus getHttpStatus();

	String getCode();

	String getLabel();
}
