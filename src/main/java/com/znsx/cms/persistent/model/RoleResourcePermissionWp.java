package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionWp
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:00:40
 */
public class RoleResourcePermissionWp extends RoleResourcePermission implements
		Serializable {

	private static final long serialVersionUID = -5296517187276485604L;

	private ControlDeviceWp wp;

	public ControlDeviceWp getWp() {
		return wp;
	}

	public void setWp(ControlDeviceWp wp) {
		this.wp = wp;
	}

}
