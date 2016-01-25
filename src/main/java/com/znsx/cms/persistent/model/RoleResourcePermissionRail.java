package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionRail
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:01:55
 */
public class RoleResourcePermissionRail extends RoleResourcePermission
		implements Serializable {

	private static final long serialVersionUID = 3290655733987344060L;
	private ControlDeviceRail rail;

	public ControlDeviceRail getRail() {
		return rail;
	}

	public void setRail(ControlDeviceRail rail) {
		this.rail = rail;
	}

}
