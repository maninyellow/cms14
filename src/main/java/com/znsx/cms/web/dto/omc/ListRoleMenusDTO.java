package com.znsx.cms.web.dto.omc;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询角色的视图权限接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:37:04
 */
public class ListRoleMenusDTO extends BaseDTO {
	private String menuIds;

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

}
