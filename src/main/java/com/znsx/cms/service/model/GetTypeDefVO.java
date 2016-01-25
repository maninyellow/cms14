package com.znsx.cms.service.model;

/**
 * GetTypeDefVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:36:32
 */
public class GetTypeDefVO {
	private String id;
	private String type;
	private String typeName;
	private String subType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	private String subTypeName;
}
