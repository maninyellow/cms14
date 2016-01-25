package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * UserOperationLog
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:37:06
 */
public class UserOperationLog implements Serializable {

	private static final long serialVersionUID = -1050136774677407505L;
	private String id;
	private Long createTime;
	private String operationType;
	private String operationName;
	private SoundRecordLog soundRecordLog;
	private String userId;
	private String userName;
	private String classesId;
	private String classesName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public SoundRecordLog getSoundRecordLog() {
		return soundRecordLog;
	}

	public void setSoundRecordLog(SoundRecordLog soundRecordLog) {
		this.soundRecordLog = soundRecordLog;
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

	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}

	public String getClassesName() {
		return classesName;
	}

	public void setClassesName(String classesName) {
		this.classesName = classesName;
	}

}
