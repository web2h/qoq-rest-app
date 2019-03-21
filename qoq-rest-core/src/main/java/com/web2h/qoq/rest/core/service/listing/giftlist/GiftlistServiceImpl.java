package com.web2h.qoq.rest.core.service.listing.giftlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web2.qoq.rest.model.entity.listing.giftlist.Giftlist;
import com.web2h.qoq.rest.core.logger.listing.GiftlistLogger;
import com.web2h.qoq.rest.core.logger.listing.ListingLogger;
import com.web2h.qoq.rest.core.service.listing.ListingServiceImpl;
import com.web2h.qoq.rest.persistence.repository.listing.ListingRepository;
import com.web2h.qoq.rest.persistence.repository.listing.giftlist.GiftlistRepository;

@Service("giftlistService")
@Transactional(rollbackFor = Exception.class)
public class GiftlistServiceImpl extends ListingServiceImpl<Giftlist> implements GiftlistService {

	@Autowired
	private GiftlistRepository giftlistRepository;

	@Autowired
	private GiftlistLogger giftlistLogger;

	@Override
	public ListingRepository<Giftlist> getListingDao() {
		return giftlistRepository;
	}

	@Override
	public ListingLogger<Giftlist> getLogger() {
		return giftlistLogger;
	}
}