package com.web2.qoq.rest.model.entity.listing.giftlist;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.web2.qoq.rest.model.entity.listing.Listing;

@Entity
@DiscriminatorValue("G")
public class Giftlist extends Listing {

	@Column(name = "hidden")
	private boolean hidden = false;

	@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Gift> gifts = new HashSet<>();

	public void addGift(Gift gift) {
		gifts.add(gift);
	}

	public void emptyGifts() {
		gifts.clear();
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public Set<Gift> getGifts() {
		return gifts;
	}
}
