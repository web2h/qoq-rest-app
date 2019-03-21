package com.web2h.qoq.rest.core.mailer;

import com.web2.qoq.rest.model.entity.listing.Listing;

public interface Mailer {

	void sendListingCreationEmail(Listing listing);
}
