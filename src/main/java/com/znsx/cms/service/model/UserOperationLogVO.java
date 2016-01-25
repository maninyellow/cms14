package com.znsx.cms.service.model;

/**
 * UserOperationLogVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午10:38:45
 */
public class UserOperationLogVO {
	private String id;
	private String createTime;
	private String operationType;
	private String operationName;
	private String soundRecordName;
	private String userName;
	private String classesName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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

	public String getSoundRecordName() {
		return soundRecordName;
	}

	public void setSoundRecordName(String soundRecordName) {
		this.soundRecordName = soundRecordName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClassesName() {
		return classesName;
	}

	public void setClassesName(String classesName) {
		this.classesName = classesName;
	}

}
