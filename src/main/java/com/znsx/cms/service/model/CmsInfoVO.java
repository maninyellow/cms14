package com.znsx.cms.service.model;

/**
 * 可变信息标志信息查询结果对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-16 上午10:22:56
 */
public class CmsInfoVO {
	private String standardNumber;
	private String name;
	private String navigation;
	private String stakeNumber;
	private String organName;
	private String subType;

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

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
		if (null == subType) {
			this.subType = "";
		}
	}

}
