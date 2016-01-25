package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 设备报警实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午5:24:13
 */
public class DeviceAlarm implements Serializable {

	private static final long serialVersionUID = 4422133549156000115L;
	private Long alarmTime;
	private Long confirmTime;
	private Long recoverTime;
	private String id;
	private Integer confirmFlag;
	private Integer recordFlag;
	private String deviceType;
	private String standardNumber;
	private String deviceId;
	private String organId;
	private String alarmType;
	private String confirmUser;
	private String deviceName;
	private String alarmTypeName;
	private String alarmContent;
	private String eventReference;
	private String note;
	private String stakeNumber;
	private String navigation;
	private String maintainUser;
	private String currentValue;
	private String threshold;
	private String status;
	private String recoverUser;

	public Long getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Long alarmTime) {
		this.alarmTime = alarmTime;
	}

	public Long getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Long confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(Integer confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public Integer getRecordFlag() {
		return recordFlag;
	}

	public void setRecordFlag(Integer recordFlag) {
		this.recordFlag = recordFlag;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getAlarmTypeName() {
		return alarmTypeName;
	}

	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getEventReference() {
		return eventReference;
	}

	public void setEventReference(String eventReference) {
		this.eventReference = eventReference;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(Long recoverTime) {
		this.recoverTime = recoverTime;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public String getMaintainUser() {
		return maintainUser;
	}

	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecoverUser() {
		return recoverUser;
	}

	public void setRecoverUser(String recoverUser) {
		this.recoverUser = recoverUser;
	}

}
