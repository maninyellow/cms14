package com.znsx.cms.persistent.model;

/**
 * UserSessionCcs
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午1:46:21
 */
public class UserSessionCcs extends UserSession {

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}

	private static final long serialVersionUID = -8468299301533967932L;
	private Ccs ccs;
}
