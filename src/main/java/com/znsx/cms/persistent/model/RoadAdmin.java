package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 路政部门
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午2:15:13
 */
public class RoadAdmin extends EmUnit implements Serializable {
	private static final long serialVersionUID = 8092224150697027410L;
	private Short carNumber;
	private Short teamNumber;

	public Short getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(Short carNumber) {
		this.carNumber = carNumber;
	}

	public Short getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(Short teamNumber) {
		this.teamNumber = teamNumber;
	}

}
