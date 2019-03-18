package com.web2h.qoq.rest.core.error;

public class ApplicationException extends Exception {

	private ApplicationError applicationError;

	private static final long serialVersionUID = 1L;

	public ApplicationException(ApplicationError applicationError) {
		super(applicationError.getLabel());
		this.applicationError = applicationError;
	}

	public ApplicationError getApplicationError() {
		return applicationError;
	}
}