package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionFd
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:56:02
 */
public class RoleResourcePermissionFd extends RoleResourcePermission implements
		Serializable {
	private static final long serialVersionUID = -6436918229958308574L;
	private FireDetector fd;

	public FireDetector getFd() {
		return fd;
	}

	public void setFd(FireDetector fd) {
		this.fd = fd;
	}

}
