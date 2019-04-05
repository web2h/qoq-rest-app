package com.web2h.qoq.rest.messaging.listing.pot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.web2.qoq.rest.model.entity.listing.pot.Pot;
import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.core.service.listing.pot.PotService;
import com.web2h.qoq.rest.messaging.AuthenticatedMessageAdapter;
import com.web2h.qoq.rest.messaging.element.listing.ListingInfoElement;
import com.web2h.qoq.rest.service.error.ApplicationException;
import com.web2h.tools.date.DateTools;

@Component
public class PotCreationMessageAdapter implements AuthenticatedMessageAdapter<PotCreationRequest, PotCreationResponse> {

	@Autowired
	private PotService potService;

	@Override
	public PotCreationResponse callService(PotCreationRequest request, User user) throws ApplicationException {

		Pot pot = new Pot();
		pot.setTitle(request.getPot().getTitle());
		pot.setDeadline(DateTools.parseUtcDate(request.getPot().getDeadline()));
		pot.setPrecision(request.getPot().getPrecision());

		Pot createdPot = potService.create(pot, user);

		return buildResponse(createdPot);
	}

	private PotCreationResponse buildResponse(Pot pot) {
		PotCreationResponse response = new PotCreationResponse();
		ListingInfoElement potInfo = new ListingInfoElement();

		potInfo.setId(pot.getId());
		potInfo.setTitle(pot.getTitle());
		potInfo.setDeadline(DateTools.formatUtcDate(pot.getDeadline()));
		potInfo.setPrecision(pot.getPrecision());
		potInfo.setAdminKey(pot.getAdminKey());
		potInfo.setPublicKey(pot.getPublicKey());

		response.setPot(potInfo);
		return response;
	}

}
