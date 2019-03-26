package com.web2.qoq.rest.model.entity.listing.giftlist;

import static com.web2.qoq.rest.model.entity.FieldLength.NAME_MAX;
import static com.web2.qoq.rest.model.entity.FieldLength.PRECISION_MAX;
import static com.web2.qoq.rest.model.entity.FieldLength.TITLE_MAX;
import static com.web2.qoq.rest.model.entity.FieldLength.WEB_URL_MAX;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.web2.qoq.rest.model.entity.VersionableEntity;
import com.web2.qoq.rest.model.entity.listing.Listing;

@Entity
@Table(name = "gifts")
public class Gift extends VersionableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title", length = TITLE_MAX, nullable = false)
	private String title;

	@Column(name = "store", length = NAME_MAX)
	private String store;

	@Column(name = "web_page", length = WEB_URL_MAX)
	private String webPage;

	@Column(name = "estimated_price")
	private Double estimatedPrice;

	@Column(name = "precision", length = PRECISION_MAX)
	private String precision;

	@ManyToOne
	@JoinColumn(name = "listing_id", nullable = false)
	private Listing listing;

	@OneToMany(mappedBy = "gift", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GiftlistContribution> contributions = new HashSet<>();

	public void addContribution(GiftlistContribution contribution) {
		contributions.add(contribution);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public Double getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(Double estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public Set<GiftlistContribution> getContributions() {
		return contributions;
	}
}
