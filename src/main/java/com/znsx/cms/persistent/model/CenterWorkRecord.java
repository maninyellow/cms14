package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 中心当班情况记录
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-26 上午9:33:01
 */
public class CenterWorkRecord implements Serializable {
	private static final long serialVersionUID = -1833221050018178564L;
	private String id;
	private String userId;
	private String userName;
	private String monitorUsers;
	private String maintainUsers;
	private String note;
	private Timestamp updateTime;
	private Timestamp recordTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
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

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

}
