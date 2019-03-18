package com.web2h.qoq.rest.core.service.authentication;

import com.web2h.qoq.rest.core.error.ApplicationException;

public interface AuthenticationService {

	String authenticateUser(String authenticationToken) throws ApplicationException;

	String confirmUser(String code, String email) throws ApplicationException;

	void requestLoginCode(String email) throws ApplicationException;
}
