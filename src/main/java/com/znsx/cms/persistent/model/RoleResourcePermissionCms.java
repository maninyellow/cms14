package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * ControlDevice的权限对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午2:19:08
 */
public class RoleResourcePermissionCms extends RoleResourcePermission implements
		Serializable {

	private static final long serialVersionUID = -1529810467950736391L;

	private ControlDeviceCms cms;

	public ControlDeviceCms getCms() {
		return cms;
	}

	public void setCms(ControlDeviceCms cms) {
		this.cms = cms;
	}

}
