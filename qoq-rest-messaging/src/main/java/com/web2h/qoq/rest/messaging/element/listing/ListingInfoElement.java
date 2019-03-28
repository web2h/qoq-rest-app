package com.web2h.qoq.rest.messaging.element.listing;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ListingInfoElement {

	@NotNull
	private String title;

	@NotNull
	private Date deadline;

	private String precision;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
}
