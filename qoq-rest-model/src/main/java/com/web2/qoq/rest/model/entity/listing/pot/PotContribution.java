package com.web2.qoq.rest.model.entity.listing.pot;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.web2.qoq.rest.model.entity.listing.Contribution;

@Entity
@DiscriminatorValue("PC")
public class PotContribution extends Contribution {

	@Column(name = "amount")
	private Double amount;

	@ManyToOne
	@JoinColumn(name = "pot_id", nullable = false)
	private Pot pot;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Pot getPot() {
		return pot;
	}

	public void setPot(Pot pot) {
		this.pot = pot;
	}
}