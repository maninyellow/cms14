package com.znsx.cms.service.model;

/**
 * 光强检测器信息查询结果对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-16 上午10:16:31
 */
public class LoliInfoVO {
	private String standardNumber;
	private String name;
	private String navigation;
	private String stakeNumber;
	private String organName;
	private String loUpLimit;
	private String loLowLimit;
	private String liUpLimit;
	private String liLowLimit;

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

	public String getLoUpLimit() {
		return loUpLimit;
	}

	public void setLoUpLimit(String loUpLimit) {
		this.loUpLimit = loUpLimit;
		if (null == loUpLimit) {
			this.loUpLimit = "";
		}
	}

	public String getLoLowLimit() {
		return loLowLimit;
	}

	public void setLoLowLimit(String loLowLimit) {
		this.loLowLimit = loLowLimit;
		if (null == loLowLimit) {
			this.loLowLimit = "";
		}
	}

	public String getLiUpLimit() {
		return liUpLimit;
	}

	public void setLiUpLimit(String liUpLimit) {
		this.liUpLimit = liUpLimit;
		if (null == liUpLimit) {
			this.liUpLimit = "";
		}
	}

	public String getLiLowLimit() {
		return liLowLimit;
	}

	public void setLiLowLimit(String liLowLimit) {
		this.liLowLimit = liLowLimit;
		if (null == liLowLimit) {
			this.liLowLimit = "";
		}
	}

}
