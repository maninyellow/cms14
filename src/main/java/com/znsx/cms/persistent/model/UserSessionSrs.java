package com.znsx.cms.persistent.model;

/**
 * UserSessionSrs
 * 
 * @author sjt
 *         <p />
 *         Create at 2014 上午9:21:01
 */
public class UserSessionSrs extends UserSession {

	private static final long serialVersionUID = 7888301132459561476L;
	private Srs srs;
	
	public Srs getSrs() {
		return srs;
	}
	public void setSrs(Srs srs) {
		this.srs = srs;
	}


}
