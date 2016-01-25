package com.znsx.cms.service.model;

/**
 * DeviceAlarmStatusVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015-6-19 上午10:32:05
 */
public class DeviceAlarmStatusVO {
	private String deviceName;
	private String organName;
	private String alarmAll;
	private String alarmTrue;
	private String alarmFalse;
	private String offlineDuration;
	private String isOnline;
	private String standardNumber;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getAlarmAll() {
		return alarmAll;
	}

	public void setAlarmAll(String alarmAll) {
		this.alarmAll = alarmAll;
	}

	public String getAlarmTrue() {
		return alarmTrue;
	}

	public void setAlarmTrue(String alarmTrue) {
		this.alarmTrue = alarmTrue;
	}

	public String getAlarmFalse() {
		return alarmFalse;
	}

	public void setAlarmFalse(String alarmFalse) {
		this.alarmFalse = alarmFalse;
	}

	public String getOfflineDuration() {
		return offlineDuration;
	}

	public void setOfflineDuration(String offlineDuration) {
		this.offlineDuration = offlineDuration;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

}
