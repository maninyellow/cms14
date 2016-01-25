package com.znsx.cms.persistent.model;

/**
 * UserSessionUser
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午1:34:51
 */
public class UserSessionUser extends UserSession {
	private static final long serialVersionUID = -5852810473383446066L;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
