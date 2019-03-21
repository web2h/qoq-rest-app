package com.web2h.qoq.rest.persistence.repository.listing;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.web2.qoq.rest.model.entity.listing.Listing;

public interface ListingRepository<L extends Listing> extends CrudRepository<L, Long> {

	@Query("SELECT listing FROM listings listing WHERE listing.adminKey = :key OR listing.publicKey = :key")
	Optional<L> findByKey(String key);

	Optional<L> findByAdminKey(String adminKey);

	Optional<L> findByPublicKey(String publicKey);
}
