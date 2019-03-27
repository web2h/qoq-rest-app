package com.web2h.qoq.rest.core.service.authentication;

import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.messaging.error.ApplicationException;

public interface AuthenticationService {

	User authenticateUser(String authenticationToken) throws ApplicationException;

	User confirmUser(String code, String email) throws ApplicationException;

	void requestLoginCode(String email) throws ApplicationException;
}
