package com.web2h.qoq.rest.core.service.listing.giftlist;

import static com.web2h.qoq.rest.core.error.NotFoundApplicationError.USER_DOES_NOT_EXIST;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.web2.qoq.rest.model.entity.listing.giftlist.Giftlist;
import com.web2h.qoq.rest.core.error.ApplicationException;
import com.web2h.qoq.rest.core.logger.listing.GiftlistLogger;
import com.web2h.qoq.rest.core.service.listing.ListingServiceUnitTest;
import com.web2h.qoq.rest.persistence.repository.listing.giftlist.GiftlistRepository;

@RunWith(MockitoJUnitRunner.class)
public class GiftlistServiceUnitTest extends ListingServiceUnitTest {

	@InjectMocks
	private GiftlistServiceImpl giftlistService;

	@Mock
	private GiftlistRepository giftlistRepository;

	@Mock
	private GiftlistLogger giftlistLogger;

	@Before
	public void setUp() {
		super.setUp();
		when(giftlistRepository.findByKey(anyString())).thenReturn(Optional.empty());
	}

	@Test
	public void givenUnknownUserWhenCreateThenApplicationExceptionIsThrown() throws ApplicationException {

		// Given
		when(userRepository.findByEmail(UNKNOWN_USER_EMAIL)).thenReturn(Optional.empty());

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(USER_DOES_NOT_EXIST.getLabel());

		// When
		giftlistService.create(givenGiftlist(), givenUnknownUser());

		// Then
		verify(giftlistRepository, never()).save(any(Giftlist.class));
	}

	@Test
	public void givenValidGiftlistWhenCreateThenSucceeds() throws ApplicationException {

		// Given
		Giftlist giftlist = givenGiftlist();
		when(giftlistRepository.save(any(Giftlist.class))).thenReturn(giftlist);

		// When
		giftlist = giftlistService.create(giftlist, givenUser());

		// Then
		assertNotNull(giftlist);
		assertNotNull(giftlist.getPublicKey());
		verify(giftlistRepository).save(any(Giftlist.class));
	}

	private Giftlist givenGiftlist() {
		Giftlist giftlist = new Giftlist();

		return giftlist;
	}
}
