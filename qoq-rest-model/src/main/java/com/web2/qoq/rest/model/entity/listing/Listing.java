package com.web2.qoq.rest.model.entity.listing;

import static com.web2.qoq.rest.model.entity.FieldLength.KEY_MAX;
import static com.web2.qoq.rest.model.entity.FieldLength.PRECISION_MAX;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.web2.qoq.rest.model.entity.FieldLength;
import com.web2.qoq.rest.model.entity.VersionableEntity;
import com.web2.qoq.rest.model.entity.user.User;

@Entity
@Table(name = "listings")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 1)
@DiscriminatorValue(value = "L")
public class Listing extends VersionableEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	@Column(name = "title", length = FieldLength.TITLE_MAX, nullable = false)
	protected String title;
	
	@Column(name = "key_public", length = KEY_MAX, nullable = false, unique = true)	
	protected String publicKey;

	@Column(name = "key_admin", length = KEY_MAX, nullable = false, unique = true)	
	protected String adminKey;
	
	@Column(name = "deadline", nullable = false)
	protected Date deadline;

	@Column(name = "precision", length = PRECISION_MAX)
	protected String precision;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	protected User administrator;
	
	@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Guest> guests = new HashSet<>();
	
	public void addGuest(Guest guest) {
		guests.add(guest);
	}

	public void emptyGuests() {
		guests.clear();
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

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getAdminKey() {
		return adminKey;
	}

	public void setAdminKey(String adminKey) {
		this.adminKey = adminKey;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public User getAdministrator() {
		return administrator;
	}

	public void setAdministrator(User administrator) {
		this.administrator = administrator;
	}

	public Set<Guest> getGuests() {
		return guests;
	}
}