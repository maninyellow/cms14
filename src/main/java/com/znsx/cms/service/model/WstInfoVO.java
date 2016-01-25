package com.znsx.cms.service.model;

/**
 * 气象检测器信息查询结果对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-16 上午9:11:43
 */
public class WstInfoVO {
	private String standardNumber;
	private String name;
	private String navigation;
	private String stakeNumber;
	private String organName;
	private String vLowLimit;
	private String wUpLimit;
	private String rUpLimit;
	private String sUpLimit;

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

	public String getVLowLimit() {
		return vLowLimit;
	}

	public void setVLowLimit(String vLowLimit) {
		this.vLowLimit = vLowLimit;
		if (null == vLowLimit) {
			this.vLowLimit = "";
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

	public String getRUpLimit() {
		return rUpLimit;
	}

	public void setRUpLimit(String rUpLimit) {
		this.rUpLimit = rUpLimit;
		if (null == rUpLimit) {
			this.rUpLimit = "";
		}
	}

	public String getSUpLimit() {
		return sUpLimit;
	}

	public void setSUpLimit(String sUpLimit) {
		this.sUpLimit = sUpLimit;
		if (null == sUpLimit) {
			this.sUpLimit = "";
		}
	}

}
