package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 系统日志码表实体对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午1:39:42
 */
public class LogOperation implements Serializable {

	private static final long serialVersionUID = -7179801319661166346L;

	private String id;
	private String operationType;
	private String operationName;
	private String operationCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

}
