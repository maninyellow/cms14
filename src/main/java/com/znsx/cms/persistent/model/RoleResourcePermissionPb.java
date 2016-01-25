package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionPb
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:03:27
 */
public class RoleResourcePermissionPb extends RoleResourcePermission implements
		Serializable {

	private static final long serialVersionUID = 7741601428745224039L;

	private PushButton pb;

	public PushButton getPb() {
		return pb;
	}

	public void setPb(PushButton pb) {
		this.pb = pb;
	}

}
