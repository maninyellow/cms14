package com.znsx.cms.service.model;

/**
 * 设备权限业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-6-19 上午10:57:37
 */
public class DevicePermissionVO {
	private String id;
	private String name;
	private String type;
	private String privilege;
	private String relevance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getRelevance() {
		return relevance;
	}

	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}

}
