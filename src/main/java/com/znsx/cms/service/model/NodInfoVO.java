package com.znsx.cms.service.model;

/**
 * 氮氧化物检测器信息查询结果对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-16 上午10:20:31
 */
public class NodInfoVO {
	private String standardNumber;
	private String name;
	private String navigation;
	private String stakeNumber;
	private String organName;
	private String noUpLimit;
	private String nooUpLimit;

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

	public String getNoUpLimit() {
		return noUpLimit;
	}

	public void setNoUpLimit(String noUpLimit) {
		this.noUpLimit = noUpLimit;
		if (null == noUpLimit) {
			this.noUpLimit = "";
		}
	}

	public String getNooUpLimit() {
		return nooUpLimit;
	}

	public void setNooUpLimit(String nooUpLimit) {
		this.nooUpLimit = nooUpLimit;
		if (null == nooUpLimit) {
			this.nooUpLimit = "";
		}
	}

}
