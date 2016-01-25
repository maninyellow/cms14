package com.znsx.cms.persistent.model;

/**
 * UserSessionRss
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-27 上午10:36:56
 */
public class UserSessionRss extends UserSession {

	private static final long serialVersionUID = -4390030835157409928L;
	private Rss rss;

	public Rss getRss() {
		return rss;
	}

	public void setRss(Rss rss) {
		this.rss = rss;
	}

}
