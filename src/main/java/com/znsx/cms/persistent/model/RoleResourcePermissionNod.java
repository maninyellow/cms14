package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionNod
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:57:39
 */
public class RoleResourcePermissionNod extends RoleResourcePermission implements
		Serializable {
	private static final long serialVersionUID = 1333772770960443314L;
	private NoDetector nod;

	public NoDetector getNod() {
		return nod;
	}

	public void setNod(NoDetector nod) {
		this.nod = nod;
	}

}
