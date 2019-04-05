package com.web2h.qoq.rest.messaging.listing.pot;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.web2h.qoq.rest.messaging.QoqResponse;
import com.web2h.qoq.rest.messaging.element.listing.ListingInfoElement;

public class PotCreationResponse extends QoqResponse {

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
