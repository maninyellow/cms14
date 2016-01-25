package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetUserVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据机构查询用户列表接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午1:54:45
 */
public class ListUserDTO extends BaseDTO {
	private List<GetUserVO> userList;
	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<GetUserVO> getUserList() {
		return userList;
	}

	public void setUserList(List<GetUserVO> userList) {
		this.userList = userList;
	}

}
