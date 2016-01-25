package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionRd
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:59:10
 */
public class RoleResourcePermissionRd extends RoleResourcePermission implements
		Serializable {

	private static final long serialVersionUID = 88524995158053154L;

	private ControlDeviceRd rd;

	public ControlDeviceRd getRd() {
		return rd;
	}

	public void setRd(ControlDeviceRd rd) {
		this.rd = rd;
	}

}
