package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionIs
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:55:49
 */
public class RoleResourcePermissionIs extends RoleResourcePermission implements
		Serializable {

	private static final long serialVersionUID = -5420442547344655748L;

	private ControlDeviceIs is;

	public ControlDeviceIs getIs() {
		return is;
	}

	public void setIs(ControlDeviceIs is) {
		this.is = is;
	}

}
