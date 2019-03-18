package com.web2.qoq.rest.model.entity.listing;

import static com.web2.qoq.rest.model.entity.FieldLength.KEY_MAX;
import static com.web2.qoq.rest.model.entity.FieldLength.NAME_MAX;

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

	@ManyToOne
	@JoinColumn(name = "user_id")
	protected User contributor;

	@Column(name = "contribution_ts", nullable = false)
	protected Date dateOfContribution = new Date();

	@Column(name = "private_key", length = KEY_MAX)
	protected String privateKey;

	@Column(name = "contributed_on_behalf_of", length = NAME_MAX)
	protected String contributedOnBehalfOf;

	@Column(name = "hidden")
	protected Boolean hidden = true;

	@PrePersist
	public void prePersist() {
		dateOfContribution = new Date();
	}

	public Boolean isHidden() {
		return hidden;
	}

	public User getContributor() {
		return contributor;
	}

	public void setContributor(User contributor) {
		this.contributor = contributor;
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
