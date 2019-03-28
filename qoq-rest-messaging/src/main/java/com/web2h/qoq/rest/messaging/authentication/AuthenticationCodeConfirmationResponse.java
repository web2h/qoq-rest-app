package com.web2h.qoq.rest.messaging.authentication;

import com.web2h.qoq.rest.messaging.QoqResponse;
import com.web2h.qoq.rest.messaging.element.listing.UserElement;

public class AuthenticationCodeConfirmationResponse extends QoqResponse {

	private UserElement user;

	public UserElement getUser() {
		return user;
	}

	public void setUser(UserElement user) {
		this.user = user;
	}
}
