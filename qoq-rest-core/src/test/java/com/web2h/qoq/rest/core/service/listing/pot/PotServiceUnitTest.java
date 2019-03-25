package com.web2h.qoq.rest.core.service.listing.pot;

import static com.web2h.qoq.rest.core.error.NotFoundApplicationError.USER_DOES_NOT_EXIST;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.web2.qoq.rest.model.entity.listing.pot.Pot;
import com.web2h.qoq.rest.core.error.ApplicationException;
import com.web2h.qoq.rest.core.logger.listing.PotLogger;
import com.web2h.qoq.rest.core.service.listing.ListingServiceUnitTest;
import com.web2h.qoq.rest.persistence.repository.listing.pot.PotRepository;

@RunWith(MockitoJUnitRunner.class)
public class PotServiceUnitTest extends ListingServiceUnitTest {

	@InjectMocks
	private PotServiceImpl potService;

	@Mock
	private PotRepository potRepository;

	@Mock
	private PotLogger potLogger;

	@Before
	public void setUp() {
		super.setUp();
		when(potRepository.findByKey(anyString())).thenReturn(Optional.empty());
	}

	@Test
	public void givenUnknownUserWhenCreateThenApplicationExceptionIsThrown() throws ApplicationException {

		// Given
		when(userRepository.findByEmail(UNKNOWN_USER_EMAIL)).thenReturn(Optional.empty());

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(USER_DOES_NOT_EXIST.getLabel());

		// When
		potService.create(givenPot(), givenUnknownUser());

		// Then
		verify(potRepository, never()).save(any(Pot.class));
	}

	@Test
	public void givenValidPotWhenCreateThenSucceeds() throws ApplicationException {

		// Given
		Pot pot = givenPot();
		when(potRepository.save(any(Pot.class))).thenReturn(pot);

		// When
		pot = potService.create(pot, givenUser());

		// Then
		assertNotNull(pot.getAdminKey());
		assertNotNull(pot.getPublicKey());
		verify(potRepository).save(any(Pot.class));
	}

	private Pot givenPot() {
		Pot pot = new Pot();

		return pot;
	}
}
