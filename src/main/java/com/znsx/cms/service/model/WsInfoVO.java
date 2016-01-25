package com.znsx.cms.service.model;

/**
 * 风速风向检测器信息查询结果对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-16 上午10:12:31
 */
public class WsInfoVO {
	private String standardNumber;
	private String name;
	private String navigation;
	private String stakeNumber;
	private String organName;
	private String wUpLimit;

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
		if (null == standardNumber) {
			this.standardNumber = "";
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (null == name) {
			this.name = "";
		}
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
		if (null == navigation) {
			this.navigation = "";
		}
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
		if (null == stakeNumber) {
			this.stakeNumber = "";
		}
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
		if (null == organName) {
			this.organName = "";
		}
	}

	public String getWUpLimit() {
		return wUpLimit;
	}

	public void setWUpLimit(String wUpLimit) {
		this.wUpLimit = wUpLimit;
		if (null == wUpLimit) {
			this.wUpLimit = "";
		}
	}

}
