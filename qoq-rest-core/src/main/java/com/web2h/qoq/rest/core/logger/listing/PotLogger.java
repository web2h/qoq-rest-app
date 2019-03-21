package com.web2h.qoq.rest.core.logger.listing;

import org.springframework.stereotype.Component;

import com.web2.qoq.rest.model.entity.listing.pot.Pot;

@Component
public class PotLogger extends ListingLogger<Pot> {

	@Override
	public void logCreation(Pot pot) {
		// TODO Implement
		super.logCreation(pot);
	}
}
