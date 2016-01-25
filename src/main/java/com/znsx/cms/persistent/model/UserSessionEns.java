package com.znsx.cms.persistent.model;

/**
 * UserSessionEns
 * @author wangbinyu <p />
 * Create at 2014 上午9:26:42
 */
public class UserSessionEns extends UserSession {

	private static final long serialVersionUID = 6109274044395451414L;
	private Ens ens;

	public Ens getEns() {
		return ens;
	}

	public void setEns(Ens ens) {
		this.ens = ens;
	}
}
