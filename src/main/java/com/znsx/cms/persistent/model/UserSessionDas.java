package com.znsx.cms.persistent.model;

/**
 * UserSessionDas
 * @author wangbinyu <p />
 * Create at 2014 上午9:25:51
 */
public class UserSessionDas extends UserSession {

	private static final long serialVersionUID = -6232963076010805831L;
	private Das das;

	public Das getDas() {
		return das;
	}

	public void setDas(Das das) {
		this.das = das;
	}
}
