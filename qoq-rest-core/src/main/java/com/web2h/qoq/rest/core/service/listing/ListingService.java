package com.web2h.qoq.rest.core.service.listing;

import com.web2.qoq.rest.model.entity.listing.Listing;
import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.core.error.ApplicationException;

public interface ListingService<L extends Listing> {

	L create(L listing, User creator) throws ApplicationException;

	L edit(L listing, User editor) throws ApplicationException;
}