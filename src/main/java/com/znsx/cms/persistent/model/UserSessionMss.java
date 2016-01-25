package com.znsx.cms.persistent.model;

/**
 * UserSessionMss
 * @author wangbinyu <p />
 * Create at 2014 上午9:22:03
 */
public class UserSessionMss extends UserSession {

	private static final long serialVersionUID = 7543530220689376028L;
	private Mss mss;

	public Mss getMss() {
		return mss;
	}

	public void setMss(Mss mss) {
		this.mss = mss;
	}
}
