package com.znsx.cms.service.model;

/**
 * 设备状态业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-17 上午11:15:11
 */
public class DeviceStatusVO {
	private String standardNumber;
	private String type;
	private String organName;
	private String navigation;
	private String stakeNumber;
	private String workStatus;
	private String commStatus;
	private String recTime;
	private String name;

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
		if (null == standardNumber) {
			this.standardNumber = "";
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		if (null == type) {
			this.type = "";
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

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
		if (null == workStatus) {
			this.workStatus = "";
		}
	}

	public String getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(String commStatus) {
		this.commStatus = commStatus;
		if (null == commStatus) {
			this.commStatus = "";
		}
	}

	public String getRecTime() {
		return recTime;
	}

	public void setRecTime(String recTime) {
		this.recTime = recTime;
		if (null == recTime) {
			this.recTime = "";
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

}
