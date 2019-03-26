package com.web2h.qoq.rest.core.logger.listing;

import org.springframework.stereotype.Component;

import com.web2.qoq.rest.model.entity.listing.giftlist.Giftlist;

@Component
public class GiftlistLogger extends ListingLogger<Giftlist> {

	@Override
	public void logCreation(Giftlist giftlist) {
		// TODO Implement
		super.logCreation(giftlist);
	}
}
