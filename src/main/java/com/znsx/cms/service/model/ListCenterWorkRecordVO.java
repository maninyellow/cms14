package com.znsx.cms.service.model;

/**
 * ListCenterWorkRecordVO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-28 下午2:34:27
 */
public class ListCenterWorkRecordVO {
	private String id;
	private String userId;
	private String userName;
	private String recordTime;
	private String monitorUsers;
	private String maintainUsers;
	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getMonitorUsers() {
		return monitorUsers;
	}

	public void setMonitorUsers(String monitorUsers) {
		this.monitorUsers = monitorUsers;
	}

	public String getMaintainUsers() {
		return maintainUsers;
	}

	public void setMaintainUsers(String maintainUsers) {
		this.maintainUsers = maintainUsers;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
