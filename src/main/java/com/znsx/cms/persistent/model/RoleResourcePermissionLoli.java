package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionLoli
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:54:26
 */
public class RoleResourcePermissionLoli extends RoleResourcePermission
		implements Serializable {
	private static final long serialVersionUID = -8378445177823369903L;
	private LoLi loli;

	public LoLi getLoli() {
		return loli;
	}

	public void setLoli(LoLi loli) {
		this.loli = loli;
	}

}
