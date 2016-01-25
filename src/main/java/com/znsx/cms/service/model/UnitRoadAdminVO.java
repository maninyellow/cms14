package com.znsx.cms.service.model;

/**
 * UnitRoadAdminVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:49:48
 */
public class UnitRoadAdminVO extends UnitVO {
	private String carNumber;
	private String teamNumber;

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(String teamNumber) {
		this.teamNumber = teamNumber;
	}
}
