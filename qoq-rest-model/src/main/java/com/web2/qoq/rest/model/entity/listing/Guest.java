package com.web2.qoq.rest.model.entity.listing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.web2.qoq.rest.model.entity.FieldLength;
import com.web2.qoq.rest.model.entity.VersionableEntity;

@Entity
@Table(name = "guests")
public class Guest extends VersionableEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", length = FieldLength.NAME_MAX, nullable = false)
	private String name;

	@Column(name = "email", length = FieldLength.EMAIL_MAX)
	private String email;

	@Column(name = "cell_phone_number", length = FieldLength.PHONE_NUMBER_MAX)
	private String cellPhoneNumber; 

	@Column(name = "notification_count")
	private Integer notificationCount = 0;

	@ManyToOne
	@JoinColumn(name = "listing_id", nullable = true)
	private Listing listing;

	public void addOneNotification() {
		notificationCount++;
	}

	public boolean hasContactInfo() {
		return !StringUtils.isEmpty(email) || !StringUtils.isEmpty(cellPhoneNumber);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public Integer getNotificationCount() {
		return notificationCount;
	}

	public void setNotificationCount(Integer notificationCount) {
		this.notificationCount = notificationCount;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}
}