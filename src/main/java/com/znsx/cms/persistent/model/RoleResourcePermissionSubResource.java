package com.znsx.cms.persistent.model;

/**
 * RoleResourcePermissionSubResource
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-2 上午11:14:55
 */
public class RoleResourcePermissionSubResource extends RoleResourcePermission {
	private static final long serialVersionUID = 1102439133354977833L;
	private SubPlatformResource subResource;

	public SubPlatformResource getSubResource() {
		return subResource;
	}

	public void setSubResource(SubPlatformResource subResource) {
		this.subResource = subResource;
	}

}
