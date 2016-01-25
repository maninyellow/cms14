package com.znsx.cms.service.model;

/**
 * UnitHospitalVO
 * @author wangbinyu <p />
 * Create at 2014 上午11:48:10
 */
public class UnitHospitalVO extends UnitVO {
	private String ambulanceNumber;
	private String unitLevel;
	private String rescueCapability;

	public String getAmbulanceNumber() {
		return ambulanceNumber;
	}

	public void setAmbulanceNumber(String ambulanceNumber) {
		this.ambulanceNumber = ambulanceNumber;
	}

	public String getUnitLevel() {
		return unitLevel;
	}

	public void setUnitLevel(String unitLevel) {
		this.unitLevel = unitLevel;
	}

	public String getRescueCapability() {
		return rescueCapability;
	}

	public void setRescueCapability(String rescueCapability) {
		this.rescueCapability = rescueCapability;
	}
}
