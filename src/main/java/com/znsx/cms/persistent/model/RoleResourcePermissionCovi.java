package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionCovi
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:47:12
 */
public class RoleResourcePermissionCovi extends RoleResourcePermission
		implements Serializable {
	private static final long serialVersionUID = -4427724710320956926L;
	private Covi covi;

	public Covi getCovi() {
		return covi;
	}

	public void setCovi(Covi covi) {
		this.covi = covi;
	}

}
