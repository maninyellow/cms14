package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Team implements Serializable {
	private static final long serialVersionUID = -7082498514771856417L;
	private String id;
	private String name;
	private String standardNumber;
	private EmUnit unit;
	private String type;
	private String note;
	private Set<ResourceUser> users = new HashSet<ResourceUser>();

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

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public EmUnit getUnit() {
		return unit;
	}

	public void setUnit(EmUnit unit) {
		this.unit = unit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<ResourceUser> getUsers() {
		return users;
	}

	public void setUsers(Set<ResourceUser> users) {
		this.users = users;
	}

}
