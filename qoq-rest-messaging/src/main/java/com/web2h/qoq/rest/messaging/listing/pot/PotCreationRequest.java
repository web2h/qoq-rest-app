package com.web2h.qoq.rest.messaging.listing.pot;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.web2h.qoq.rest.messaging.QoqRequest;
import com.web2h.qoq.rest.messaging.element.listing.ListingInfoElement;

public class PotCreationRequest extends QoqRequest {

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
