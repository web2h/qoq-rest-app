package com.web2h.qoq.rest.core.service.listing;

import java.util.Optional;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.core.mailer.Mailer;
import com.web2h.qoq.rest.persistence.repository.user.UserRepository;

public class ListingServiceUnitTest {

	protected static final String USER_EMAIL = "user@email.com";

	protected static final String UNKNOWN_USER_EMAIL = "unknown.user@email.com";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	protected UserRepository userRepository;

	@Mock
	protected Mailer mailer;

	public void setUp() {
		Mockito.when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(givenUser()));
	}

	protected User givenUser() {
		User user = new User(USER_EMAIL);

		return user;
	}

	protected User givenUnknownUser() {
		User user = new User(UNKNOWN_USER_EMAIL);

		return user;
	}
}
