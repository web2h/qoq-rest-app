package com.web2.qoq.rest.model.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Abstract mapped entity class to specify that an entity has a version number.
 */
@MappedSuperclass
public abstract class VersionableEntity {

	/** Entity version. */
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}
}
