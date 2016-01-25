package com.znsx.cms.service.model;

/**
 * DeviceOnlineHistroyVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:51:12
 */
public class DeviceOnlineHistroyVO {
	private String onlineTime;
	private String offlineTime;
	private String confirmUser;
	private String confirmTime;
	private String recoverUser;
	private String deviceName;
	private String organName;
	private String maintainUser;
	private String alarmContent;
	private String status;
	private String offlineDuration;
	private String standardNumber;

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getRecoverUser() {
		return recoverUser;
	}

	public void setRecoverUser(String recoverUser) {
		this.recoverUser = recoverUser;
	}

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

	public String getMaintainUser() {
		return maintainUser;
	}

	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOfflineDuration() {
		return offlineDuration;
	}

	public void setOfflineDuration(String offlineDuration) {
		this.offlineDuration = offlineDuration;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

}
