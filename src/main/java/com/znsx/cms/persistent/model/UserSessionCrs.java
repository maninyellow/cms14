package com.znsx.cms.persistent.model;

/**
 * UserSessionCrs
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午9:21:01
 */
public class UserSessionCrs extends UserSession {

	private static final long serialVersionUID = 7888301132459561476L;
	private Crs crs;

	public Crs getCrs() {
		return crs;
	}

	public void setCrs(Crs crs) {
		this.crs = crs;
	}

}
