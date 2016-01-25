package com.znsx.cms.persistent.model;

/**
 * UserSessionRms
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:08:20
 */
public class UserSessionRms extends UserSession {

	private static final long serialVersionUID = -6274518579885072830L;
	private Rms rms;

	public Rms getRms() {
		return rms;
	}

	public void setRms(Rms rms) {
		this.rms = rms;
	}

}
