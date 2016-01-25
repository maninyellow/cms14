package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.UserRoleVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询用户关联的角色接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午10:18:13
 */
public class ListUserRoleDTO extends BaseDTO {
	private List<UserRoleVO> roleList;

	public List<UserRoleVO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UserRoleVO> roleList) {
		this.roleList = roleList;
	}

}
