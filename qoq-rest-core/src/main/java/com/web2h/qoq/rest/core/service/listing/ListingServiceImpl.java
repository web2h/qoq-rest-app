package com.web2h.qoq.rest.core.service.listing;

import static com.web2h.qoq.rest.service.error.NotFoundApplicationError.USER_DOES_NOT_EXIST;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.web2.qoq.rest.model.entity.FieldLength;
import com.web2.qoq.rest.model.entity.listing.Listing;
import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.core.logger.listing.ListingLogger;
import com.web2h.qoq.rest.core.mailer.Mailer;
import com.web2h.qoq.rest.persistence.repository.listing.ListingRepository;
import com.web2h.qoq.rest.persistence.repository.user.UserRepository;
import com.web2h.qoq.rest.service.error.ApplicationException;

@Transactional(readOnly = false, rollbackFor = Exception.class)
public abstract class ListingServiceImpl<L extends Listing> implements ListingService<L> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	protected Mailer mailer;

	public L create(L listing, User creator) throws ApplicationException {

		Validate.notNull(listing, "The listing cannot be null when requesting a creation");
		Validate.notNull(creator, "A creator must be provided");

		userRepository.findByEmail(creator.getEmail()).orElseThrow(() -> new ApplicationException(USER_DOES_NOT_EXIST));

		// Setting the keys
		listing.setAdminKey(buildUniqueKey());
		listing.setPublicKey(buildUniqueKey());

		L createdListing = getListingDao().save(listing);
		getLogger().logCreation(createdListing);
		mailer.sendListingCreationEmail(createdListing);

		return createdListing;
	}

	public L edit(L listing, User editor) throws ApplicationException {
		return listing;
	}

	public abstract ListingRepository<L> getListingDao();

	public abstract ListingLogger<L> getLogger();

	protected String buildUniqueKey() {
		do {
			String uniqueKey = RandomStringUtils.random(FieldLength.KEY_MAX, "abcdefghjkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789");
			if (!getListingDao().findByKey(uniqueKey).isPresent()) {
				return uniqueKey;
			}
		} while (true);
	}
}
