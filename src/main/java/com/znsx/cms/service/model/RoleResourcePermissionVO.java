package com.znsx.cms.service.model;

/**
 * 接收sevice层返回的设备ID和设备权限对象类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午5:29:44
 */
public class RoleResourcePermissionVO {
	private String deviceId;
	private String privilege;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

}
