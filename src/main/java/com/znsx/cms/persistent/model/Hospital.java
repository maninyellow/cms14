package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 协助单位-医院
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午2:06:05
 */
public class Hospital extends EmUnit implements Serializable {
	private static final long serialVersionUID = -2942444580696159827L;
	private Short ambulanceNumber;
	private Short unitLevel;
	private Short rescueCapability;

	public Short getAmbulanceNumber() {
		return ambulanceNumber;
	}

	public void setAmbulanceNumber(Short ambulanceNumber) {
		this.ambulanceNumber = ambulanceNumber;
	}

	public Short getUnitLevel() {
		return unitLevel;
	}

	public void setUnitLevel(Short unitLevel) {
		this.unitLevel = unitLevel;
	}

	public Short getRescueCapability() {
		return rescueCapability;
	}

	public void setRescueCapability(Short rescueCapability) {
		this.rescueCapability = rescueCapability;
	}

}
