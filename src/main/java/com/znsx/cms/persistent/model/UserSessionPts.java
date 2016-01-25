package com.znsx.cms.persistent.model;

/**
 * UserSessionPts
 * @author wangbinyu <p />
 * Create at 2014 上午9:25:00
 */
public class UserSessionPts extends UserSession {

	private static final long serialVersionUID = 2486699114582330354L;
	private Pts pts;

	public Pts getPts() {
		return pts;
	}

	public void setPts(Pts pts) {
		this.pts = pts;
	}
}
