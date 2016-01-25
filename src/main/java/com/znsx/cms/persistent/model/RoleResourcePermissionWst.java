package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * RoleResourcePermissionWst
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午4:52:42
 */
public class RoleResourcePermissionWst extends RoleResourcePermission implements
		Serializable {
	private static final long serialVersionUID = 147027467900762832L;
	private WeatherStat wst;

	public WeatherStat getWst() {
		return wst;
	}

	public void setWst(WeatherStat wst) {
		this.wst = wst;
	}

}
