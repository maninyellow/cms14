package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionFan
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:25:39
 */
public class RoleResourcePermissionFan extends RoleResourcePermission implements
		Serializable {
	private static final long serialVersionUID = -6585884101418449114L;
	private ControlDeviceFan fan;

	public ControlDeviceFan getFan() {
		return fan;
	}

	public void setFan(ControlDeviceFan fan) {
		this.fan = fan;
	}

}
