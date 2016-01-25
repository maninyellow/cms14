package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.RoleVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询角色的资源操作权限接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:54:29
 */
public class ListRoleResourcesDTO extends BaseDTO {
	private RoleVO role;
	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public RoleVO getRole() {
		return role;
	}

	public void setRole(RoleVO role) {
		this.role = role;
	}

}
