package com.web2h.qoq.messaging.listing.pot;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.web2.qoq.rest.model.entity.listing.pot.Pot;
import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.core.service.listing.pot.PotService;
import com.web2h.qoq.rest.messaging.element.listing.ListingInfoElement;
import com.web2h.qoq.rest.messaging.listing.pot.PotCreationMessageAdapter;
import com.web2h.qoq.rest.messaging.listing.pot.PotCreationRequest;
import com.web2h.qoq.rest.messaging.listing.pot.PotCreationResponse;
import com.web2h.qoq.rest.service.error.ApplicationException;
import com.web2h.tools.date.DateTools;

@RunWith(MockitoJUnitRunner.class)
public class PotCreationMessageAdapterUnitTest {

	private static final Long POT_ID = 1l;

	private static final String POT_TITLE = "pot.title";

	private static final String POT_PRECISION = "pot.precision";

	private static final String POT_DEADLINE = "2037-12-03T10:15:30.000Z";

	private static final String POT_ADMIN_KEY = "pot.admin.key";

	private static final String POT_PUBLIC_KEY = "pot.public.key";

	@InjectMocks
	private PotCreationMessageAdapter potCreationMessageAdapter;

	@Mock
	private PotService potService;

	@Test
	public void givenValidPotWhenCallServiceThenResponseIsBuilt() throws ApplicationException {

		// Given
		PotCreationRequest request = givenPotCreationRequest();
		User user = givenUser();
		Mockito.when(potService.create(any(Pot.class), any(User.class))).thenReturn(givenExistingPot());

		// When
		PotCreationResponse response = potCreationMessageAdapter.callService(request, user);

		// Then
		assertEquals(POT_ID, response.getPot().getId());
		assertEquals(POT_TITLE, response.getPot().getTitle());
		assertEquals(POT_DEADLINE, response.getPot().getDeadline());
		assertEquals(POT_PRECISION, response.getPot().getPrecision());
		assertEquals(POT_ADMIN_KEY, response.getPot().getAdminKey());
		assertEquals(POT_PUBLIC_KEY, response.getPot().getPublicKey());
	}

	private Pot givenExistingPot() {
		Pot pot = new Pot();
		pot.setId(POT_ID);
		pot.setTitle(POT_TITLE);
		pot.setPrecision(POT_PRECISION);
		pot.setDeadline(DateTools.parseUtcDate(POT_DEADLINE));
		pot.setAdminKey(POT_ADMIN_KEY);
		pot.setPublicKey(POT_PUBLIC_KEY);

		return pot;
	}

	private User givenUser() {
		User user = new User();
		return user;
	}

	private Pot givenPotToCreate() {
		Pot pot = new Pot();
		pot.setTitle(POT_TITLE);
		pot.setPrecision(POT_PRECISION);
		pot.setDeadline(DateTools.parseUtcDate(POT_DEADLINE));

		return pot;
	}

	private PotCreationRequest givenPotCreationRequest() {
		PotCreationRequest request = new PotCreationRequest();
		ListingInfoElement potInfo = new ListingInfoElement();
		potInfo.setTitle(POT_TITLE);
		potInfo.setPrecision(POT_PRECISION);
		potInfo.setDeadline(POT_DEADLINE);
		request.setPot(potInfo);

		return request;
	}

}
