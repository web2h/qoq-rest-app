package com.web2h.qoq.rest.messaging.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.core.service.authentication.AuthenticationService;
import com.web2h.qoq.rest.messaging.MessageAdapter;
import com.web2h.qoq.rest.messaging.element.listing.UserElement;
import com.web2h.qoq.rest.service.error.ApplicationException;

@Component
public class AuthenticationCodeConfirmationMessageAdapter
		implements MessageAdapter<AuthenticationCodeConfirmationRequest, AuthenticationCodeConfirmationResponse> {

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public AuthenticationCodeConfirmationResponse callService(AuthenticationCodeConfirmationRequest request) throws ApplicationException {
		User user = authenticationService.confirmUser(request.getCode(), request.getEmail());

		return buildResponse(user);
	}

	private AuthenticationCodeConfirmationResponse buildResponse(User user) {
		UserElement userElement = new UserElement();
		userElement.setAlias(user.getAlias());
		userElement.setEmail(user.getEmail());
		userElement.setAuthenticationToken(user.getAuthenticationToken());
		if (user.getPreferredLanguage() != null) {
			userElement.setPreferredLanguage(user.getPreferredLanguage().name());
		}

		AuthenticationCodeConfirmationResponse response = new AuthenticationCodeConfirmationResponse();
		response.setUser(userElement);
		return response;
	}
}