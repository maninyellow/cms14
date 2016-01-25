package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionLight
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:57:48
 */
public class RoleResourcePermissionLight extends RoleResourcePermission
		implements Serializable {

	private static final long serialVersionUID = 1897651179158828767L;

	private ControlDeviceLight light;

	public ControlDeviceLight getLight() {
		return light;
	}

	public void setLight(ControlDeviceLight light) {
		this.light = light;
	}

}
