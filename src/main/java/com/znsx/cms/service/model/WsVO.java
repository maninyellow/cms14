package com.znsx.cms.service.model;

/**
 * 风速风向检测器业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-30 下午4:18:29
 */
public class WsVO {
	private String standardNumber;
	private String organName;
	private String navigation;
	private String stakeNumber;
	private String speedMax;
	private String speedAvg;
	private String speedMin;
	private String direction;
	private String recTime;
	private String name;

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public String getSpeedMax() {
		return speedMax;
	}

	public void setSpeedMax(String speedMax) {
		this.speedMax = speedMax;
	}

	public String getSpeedAvg() {
		return speedAvg;
	}

	public void setSpeedAvg(String speedAvg) {
		this.speedAvg = speedAvg;
	}

	public String getSpeedMin() {
		return speedMin;
	}

	public void setSpeedMin(String speedMin) {
		this.speedMin = speedMin;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getRecTime() {
		return recTime;
	}

	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
