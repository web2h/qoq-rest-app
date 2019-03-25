package com.web2.qoq.rest.model.entity.user;

import static com.web2.qoq.rest.model.entity.FieldLength.ALIAS_MAX;
import static com.web2.qoq.rest.model.entity.FieldLength.EMAIL_MAX;
import static com.web2.qoq.rest.model.entity.FieldLength.LANGUAGE_MAX;
import static com.web2.qoq.rest.model.entity.FieldLength.PHONE_NUMBER_MAX;
import static com.web2.qoq.rest.model.entity.FieldLength.TOKEN_MAX;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Column(name = "email", length = EMAIL_MAX, unique = true)
	private String email;

	@Column(name = "cell_phone_number", length = PHONE_NUMBER_MAX, unique = true)
	private String cellPhoneNumber;

	@Column(name = "authentication_token", length = TOKEN_MAX, unique = true)
	private String authenticationToken;

	@Column(name = "alias", length = ALIAS_MAX, nullable = false)
	private String alias;

	@Enumerated(EnumType.STRING)
	@Column(name = "preferred_language", length = LANGUAGE_MAX, nullable = false)
	private Language preferredLanguage;

	public User(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(id).append(email).append(authenticationToken);
		return hcb.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User that = (User) obj;
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(id, this.id).append(email, that.email).append(authenticationToken, this.authenticationToken);
		return eb.isEquals();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Language getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(Language preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}
}