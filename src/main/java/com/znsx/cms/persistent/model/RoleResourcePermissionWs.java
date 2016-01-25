package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionWs
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:50:57
 */
public class RoleResourcePermissionWs extends RoleResourcePermission implements
		Serializable {
	private static final long serialVersionUID = 4963339449234508569L;
	private WindSpeed ws;

	public WindSpeed getWs() {
		return ws;
	}

	public void setWs(WindSpeed ws) {
		this.ws = ws;
	}

}
