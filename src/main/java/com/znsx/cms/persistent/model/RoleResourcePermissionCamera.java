package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionCamera
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:24:26
 */
public class RoleResourcePermissionCamera extends RoleResourcePermission
		implements Serializable {
	private static final long serialVersionUID = 718083603663325399L;
	private Camera camera;

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

}
