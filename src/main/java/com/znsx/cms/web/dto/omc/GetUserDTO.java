package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetUserVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据ID查询用户返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午1:36:09
 */
public class GetUserDTO extends BaseDTO {
	private GetUserVO user;

	public GetUserVO getUser() {
		return user;
	}

	public void setUser(GetUserVO user) {
		this.user = user;
	}

}
