package com.znsx.cms.web.dto.cs;

import com.znsx.cms.service.model.CcsVO;
import com.znsx.cms.service.model.UserLogonVO;
import com.znsx.cms.service.model.UserViewVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * CS客户端用户登录接口返回对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午4:53:09
 */
public class UserLogonDTO extends BaseDTO {
	private UserLogonVO user;
	private CcsVO ccs;
	private UserViewVO auth;

	public UserLogonVO getUser() {
		return user;
	}

	public void setUser(UserLogonVO user) {
		this.user = user;
	}

	public CcsVO getCcs() {
		return ccs;
	}

	public void setCcs(CcsVO ccs) {
		this.ccs = ccs;
	}

	public UserViewVO getAuth() {
		return auth;
	}

	public void setAuth(UserViewVO auth) {
		this.auth = auth;
	}

}
