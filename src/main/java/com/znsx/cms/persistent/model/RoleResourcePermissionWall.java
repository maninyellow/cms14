/**
 * 
 */
package com.znsx.cms.persistent.model;

/**
 * @author znsx
 *
 */
public class RoleResourcePermissionWall extends RoleResourcePermission {

	private static final long serialVersionUID = -9197276152092090329L;
	private DisplayWall displayWall;

	public DisplayWall getDisplayWall() {
		return displayWall;
	}

	public void setDisplayWall(DisplayWall displayWall) {
		this.displayWall = displayWall;
	}

}
