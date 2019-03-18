package com.web2.qoq.rest.model.entity.listing;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.web2.qoq.rest.model.entity.FieldLength;
import com.web2.qoq.rest.model.entity.VersionableEntity;
import com.web2.qoq.rest.model.entity.user.User;

@Entity
@Table(name = "contributions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 2)
@DiscriminatorValue(value = "LC")
public class Contribution extends VersionableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	/** CONTRIBUTOR - As a logged in user. */
	@ManyToOne
	@JoinColumn(name = "user_id")
	protected User contributor;

	/** UNLOGGED CONTRIBUTOR - As an unlogged user. */
	@ManyToOne
	@JoinColumn(name = "unlogged_user_id")
	protected User unloggedContributor;

	/** DATE OF CONTRIBUTION - Contribution date. */
	@Column(name = "contribution_ts", nullable = false)
	protected Date dateOfContribution = new Date();

	/**
	 * PRIVATE KEY - Private key to allow unlogged user to edit or remove its
	 * contribution.
	 */
	@Column(name = "private_key", length = FieldLength.KEY_MAX)
	protected String privateKey;

	/**
	 * ON BEHALF CONTRIBUTOR - Name of the person on behalf of whom the logged in
	 * user makes a contribution.
	 */
	@Column(name = "contributed_on_behalf_of", length = FieldLength.NAME_MAX)
	protected String contributedOnBehalfOf;

	/** HIDDEN - Keep the contribution private. */
	@Column(name = "hidden")
	protected Boolean hidden = true;

	@PrePersist
	public void prePersist() {
		dateOfContribution = new Date();
	}

	/**
	 * Copies properties of a Contribution instance to the current instance.
	 * 
	 * @param contribution
	 *            Contribution instance to get the properties from
	 */
	public void copyProperties(final Contribution contribution) {
		this.hidden = contribution.isHidden();
		this.contributedOnBehalfOf = contribution.getContributedOnBehalfOf();
	}

	/**
	 * Tells if the contribution amount must remain hidden or not.
	 * 
	 * @return true if the contribution amount is hidden, false otherwise
	 */
	public Boolean isHidden() {
		return hidden;
	}

	/**
	 * Gets the contributor email (logged or unlogged).
	 * 
	 * @return The contributor email
	 */
	public String getContributorEmail() {
		if (contributor != null) {
			return contributor.getEmail();
		} else if (unloggedContributor != null) {
			return unloggedContributor.getEmail();
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Gets the contributor name (logged or unlogged).
	 * 
	 * @return The contributor name
	 */
	public String getContributorName() {
		return null;
	}

	public User getContributor() {
		return contributor;
	}

	public void setContributor(User contributor) {
		this.contributor = contributor;
	}

	public User getUnloggedContributor() {
		return unloggedContributor;
	}

	public void setUnloggedContributor(User unloggedContributor) {
		this.unloggedContributor = unloggedContributor;
	}

	public Date getDateOfContribution() {
		return dateOfContribution;
	}

	public void setDateOfContribution(Date dateOfContribution) {
		this.dateOfContribution = dateOfContribution;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public String getHiddenStringValue() {
		if (hidden == null) {
			hidden = false;
		}
		return hidden.toString();
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public String getContributedOnBehalfOf() {
		return contributedOnBehalfOf;
	}

	public void setContributedOnBehalfOf(String contributedOnBehalfOf) {
		this.contributedOnBehalfOf = contributedOnBehalfOf;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!(object instanceof Contribution)) {
			return false;
		}
		Contribution other = (Contribution) object;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
