package com.znsx.cms.persistent.model;

import java.io.Serializable;

public class Store implements Serializable {
	private static final long serialVersionUID = -4508142507501838783L;
	private String id;
	private String name;
	private Resource resource;
	private EmUnit unit;
	private String resourceNumber;
	private String minNumber;
	private String status;
	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public EmUnit getUnit() {
		return unit;
	}

	public void setUnit(EmUnit unit) {
		this.unit = unit;
	}

	public String getResourceNumber() {
		return resourceNumber;
	}

	public void setResourceNumber(String resourceNumber) {
		this.resourceNumber = resourceNumber;
	}

	public String getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(String minNumber) {
		this.minNumber = minNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
