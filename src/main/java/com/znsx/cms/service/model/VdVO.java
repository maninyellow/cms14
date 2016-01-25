package com.znsx.cms.service.model;

/**
 * 车辆监测器采集数值业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-23 下午3:00:16
 */
public class VdVO {
	private String standardNumber;
	private String organName;
	private String navigation;
	private String stakeNumber;
	private String fluxAvg;
	private String flux;
	private String speedAvg;
	private String occupAvg;
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

	public String getFluxAvg() {
		return fluxAvg;
	}

	public void setFluxAvg(String fluxAvg) {
		this.fluxAvg = fluxAvg;
	}

	public String getFlux() {
		return flux;
	}

	public void setFlux(String flux) {
		this.flux = flux;
	}

	public String getSpeedAvg() {
		return speedAvg;
	}

	public void setSpeedAvg(String speedAvg) {
		this.speedAvg = speedAvg;
	}

	public String getOccupAvg() {
		return occupAvg;
	}

	public void setOccupAvg(String occupAvg) {
		this.occupAvg = occupAvg;
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
