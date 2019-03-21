package com.web2h.qoq.rest.core.service.listing.pot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web2.qoq.rest.model.entity.listing.pot.Pot;
import com.web2h.qoq.rest.core.logger.listing.ListingLogger;
import com.web2h.qoq.rest.core.logger.listing.PotLogger;
import com.web2h.qoq.rest.core.service.listing.ListingServiceImpl;
import com.web2h.qoq.rest.persistence.repository.listing.ListingRepository;
import com.web2h.qoq.rest.persistence.repository.listing.pot.PotRepository;

@Service("potService")
@Transactional(rollbackFor = Exception.class)
public class PotServiceImpl extends ListingServiceImpl<Pot> implements PotService {

	@Autowired
	private PotRepository potRepository;

	@Autowired
	private PotLogger potLogger;

	@Override
	public ListingRepository<Pot> getListingDao() {
		return potRepository;
	}

	@Override
	public ListingLogger<Pot> getLogger() {
		return potLogger;
	}
}