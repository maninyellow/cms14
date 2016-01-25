package com.znsx.cms.persistent.model;

/**
 * UserSessionDws
 * @author wangbinyu <p />
 * Create at 2014 上午9:23:33
 */
public class UserSessionDws extends UserSession {

	private static final long serialVersionUID = -4711712780265483927L;
	private Dws dws;

	public Dws getDws() {
		return dws;
	}

	public void setDws(Dws dws) {
		this.dws = dws;
	}
}
