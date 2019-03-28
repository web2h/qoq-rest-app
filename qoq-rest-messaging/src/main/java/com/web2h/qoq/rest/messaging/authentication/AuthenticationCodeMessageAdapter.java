package com.web2h.qoq.rest.messaging.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.web2h.qoq.rest.core.service.authentication.AuthenticationService;
import com.web2h.qoq.rest.messaging.MessageAdapter;
import com.web2h.qoq.rest.service.error.ApplicationException;

@Component
public class AuthenticationCodeMessageAdapter implements MessageAdapter<AuthenticationCodeRequest, AuthenticationCodeResponse> {

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public AuthenticationCodeResponse callService(AuthenticationCodeRequest request) throws ApplicationException {
		String code = authenticationService.requestLoginCode(request.getEmail());
		return buildResponse(code);
	}

	private AuthenticationCodeResponse buildResponse(String code) {
		AuthenticationCodeResponse response = new AuthenticationCodeResponse();
		response.setCode(code);
		return response;
	}
}
