package com.web2h.qoq.rest.messaging.listing.pot.creation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.web2h.qoq.rest.messaging.AuthenticatedRequest;
import com.web2h.qoq.rest.messaging.element.listing.ListingInfoElement;

public class PotCreationRequest extends AuthenticatedRequest {

	@NotNull
	@Valid
	private ListingInfoElement pot;

	public ListingInfoElement getPot() {
		return pot;
	}

	public void setPot(ListingInfoElement pot) {
		this.pot = pot;
	}
}
