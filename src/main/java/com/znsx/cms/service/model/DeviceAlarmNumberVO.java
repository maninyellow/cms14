package com.znsx.cms.service.model;

/**
 * DeviceAlarmNumberVO
 * @author wangbinyu <p />
 * Create at 2015 上午10:22:48
 */
public class DeviceAlarmNumberVO {
	private String standardNumber;
	private int faultNumber;

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public int getFaultNumber() {
		return faultNumber;
	}

	public void setFaultNumber(int faultNumber) {
		this.faultNumber = faultNumber;
	}
}
