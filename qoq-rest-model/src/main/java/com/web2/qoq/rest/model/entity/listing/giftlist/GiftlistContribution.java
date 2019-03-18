package com.web2.qoq.rest.model.entity.listing.giftlist;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.web2.qoq.rest.model.entity.listing.Contribution;

@Entity
@Table(name = "contributions")
@DiscriminatorValue("GC")
public class GiftlistContribution extends Contribution {

	/** "No sharing" share: the user will buy the gift by himself. */
	public static final Integer COMPLETE_SHARE = 100;

	@ManyToOne
	@JoinColumn(name = "gift_id")
	private Gift gift;

	@Column(name = "share")
	private Integer share;

	public boolean isNoShare() {
		return share.equals(COMPLETE_SHARE);
	}

	public Gift getGift() {
		return gift;
	}

	public void setGift(Gift gift) {
		this.gift = gift;
	}

	public Integer getShare() {
		return share;
	}

	public void setShare(Integer share) {
		this.share = share;
	}
}