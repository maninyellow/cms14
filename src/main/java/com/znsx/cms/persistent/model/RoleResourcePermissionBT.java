package com.znsx.cms.persistent.model;

/**
 * RoleResourcePermissionBT
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午9:35:05
 */
public class RoleResourcePermissionBT extends RoleResourcePermission {
	private static final long serialVersionUID = 8481796869847195042L;

	private BoxTransformer boxTransformer;

	public BoxTransformer getBoxTransformer() {
		return boxTransformer;
	}

	public void setBoxTransformer(BoxTransformer boxTransformer) {
		this.boxTransformer = boxTransformer;
	}

}
