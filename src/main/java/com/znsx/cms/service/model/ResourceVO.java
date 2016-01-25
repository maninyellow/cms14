package com.znsx.cms.service.model;

/**
 * 方法调用的对象，有可能是User, 各个Server
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-27 下午02:30:56
 */
public class ResourceVO {
	private String id;
	private String type;
	private String name;
	private String organId;
	private String priority;
	private String realName;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
