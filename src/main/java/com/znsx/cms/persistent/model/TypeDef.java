package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 类型字典实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-1 上午10:59:56
 */
public class TypeDef implements Serializable {
	private static final long serialVersionUID = 757191695545575988L;
	private String id;
	private Integer type;
	private String typeName;
	private Integer subType;
	private String subTypeName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

}
