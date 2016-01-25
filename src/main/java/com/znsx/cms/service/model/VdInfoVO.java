package com.znsx.cms.service.model;

/**
 * 车检器信息查询结果对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-15 下午2:37:26
 */
public class VdInfoVO {
	private String standardNumber;
	private String name;
	private String navigation;
	private String stakeNumber;
	private String organName;
	private String sUpLimit;
	private String sLowLimit;
	private String oUpLimit;
	private String oLowLimit;
	private String vUpLimit;
	private String vLowLimit;

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

	public String getSUpLimit() {
		return sUpLimit;
	}

	public void setSUpLimit(String sUpLimit) {
		this.sUpLimit = sUpLimit;
		if (null == sUpLimit) {
			this.sUpLimit = "";
		}
	}

	public String getSLowLimit() {
		return sLowLimit;
	}

	public void setSLowLimit(String sLowLimit) {
		this.sLowLimit = sLowLimit;
		if (null == sLowLimit) {
			this.sLowLimit = "";
		}
	}

	public String getOUpLimit() {
		return oUpLimit;
	}

	public void setOUpLimit(String oUpLimit) {
		this.oUpLimit = oUpLimit;
		if (null == oUpLimit) {
			this.oUpLimit = "";
		}
	}

	public String getOLowLimit() {
		return oLowLimit;
	}

	public void setOLowLimit(String oLowLimit) {
		this.oLowLimit = oLowLimit;
		if (null == oLowLimit) {
			this.oLowLimit = "";
		}
	}

	public String getVUpLimit() {
		return vUpLimit;
	}

	public void setVUpLimit(String vUpLimit) {
		this.vUpLimit = vUpLimit;
		if (null == vUpLimit) {
			this.vUpLimit = "";
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

}
