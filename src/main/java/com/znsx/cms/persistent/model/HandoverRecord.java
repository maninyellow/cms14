package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 交接班记录
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-27 下午4:52:24
 */
public class HandoverRecord implements Serializable {
	private static final long serialVersionUID = 7961132528255325629L;
	private String id;
	private Timestamp beginTime;
	private Timestamp endTime;
	private String currentUserId;
	private String currentUserName;
	private String takeUserId;
	private String takeUserName;
	private String taskList;
	private String sanitation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getTakeUserId() {
		return takeUserId;
	}

	public void setTakeUserId(String takeUserId) {
		this.takeUserId = takeUserId;
	}

	public String getTakeUserName() {
		return takeUserName;
	}

	public void setTakeUserName(String takeUserName) {
		this.takeUserName = takeUserName;
	}

	public String getTaskList() {
		return taskList;
	}

	public void setTaskList(String taskList) {
		this.taskList = taskList;
	}

	public String getSanitation() {
		return sanitation;
	}

	public void setSanitation(String sanitation) {
		this.sanitation = sanitation;
	}
}
