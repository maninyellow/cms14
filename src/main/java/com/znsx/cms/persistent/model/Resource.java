package com.znsx.cms.persistent.model;

import java.io.Serializable;

public class Resource implements Serializable {
	private static final long serialVersionUID = 1375511638275956104L;
	private String id;
	private String name;
	private String standardNumber;
	private EmUnit unit;
	private String abilityType;
	private String unitType;
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

	public String getAbilityType() {
		return abilityType;
	}

	public void setAbilityType(String abilityType) {
		this.abilityType = abilityType;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
