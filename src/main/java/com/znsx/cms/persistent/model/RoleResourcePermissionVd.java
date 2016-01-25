package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionVd
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:49:11
 */
public class RoleResourcePermissionVd extends RoleResourcePermission implements
		Serializable {
	private static final long serialVersionUID = 9216384237926018150L;
	private VehicleDetector vd;

	public VehicleDetector getVd() {
		return vd;
	}

	public void setVd(VehicleDetector vd) {
		this.vd = vd;
	}

}
