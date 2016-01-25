package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 资源权限操作项实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-6-19 下午2:20:29
 */
public class ResourceOperation implements Serializable {
	private static final long serialVersionUID = -4217685635196448824L;
	private String id;
	private String resourceType;
	private String operationCode;
	private String operationName;
	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
