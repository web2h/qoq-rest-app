package com.web2.qoq.rest.model.entity.listing.pot;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.web2.qoq.rest.model.entity.listing.Listing;

@Entity
@DiscriminatorValue("P")
public class Pot extends Listing {

	@OneToMany(mappedBy = "pot", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PotContribution> contributions = new HashSet<>();

	public void addContribution(PotContribution contribution) {
		contributions.add(contribution);
	}

	public void emptyContributions() {
		contributions = new HashSet<>();
	}

	public Set<PotContribution> getContributions() {
		return contributions;
	}
}
