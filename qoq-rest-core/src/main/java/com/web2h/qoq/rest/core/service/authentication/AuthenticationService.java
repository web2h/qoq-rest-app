package com.web2h.qoq.rest.core.service.authentication;

import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.service.error.ApplicationException;

public interface AuthenticationService {

	User authenticateUser(String authenticationToken) throws ApplicationException;

	User confirmUser(String code, String email) throws ApplicationException;

	// TODO When login code is sent to user's email address, change return type to
	// void
	String requestLoginCode(String email) throws ApplicationException;
}
