package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListRoleVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询角色列表接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:31:43
 */
public class ListRoleDTO extends BaseDTO {
	private List<ListRoleVO> roleList;
	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<ListRoleVO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<ListRoleVO> roleList) {
		this.roleList = roleList;
	}

}
