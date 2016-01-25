package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetRoleVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据ID查询角色接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-6-19 下午4:15:39
 */
public class GetRoleDTO extends BaseDTO {
	private GetRoleVO role;

	public GetRoleVO getRole() {
		return role;
	}

	public void setRole(GetRoleVO role) {
		this.role = role;
	}

}
