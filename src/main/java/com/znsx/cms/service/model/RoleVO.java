package com.znsx.cms.service.model;

import java.util.List;

/**
 * 角色业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:06:37
 */
public class RoleVO {
	private String id;
	private String type;
	private String name;
	private List<ResourcePermissionVO> deviceList;

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

	public List<ResourcePermissionVO> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<ResourcePermissionVO> deviceList) {
		this.deviceList = deviceList;
	}

}
