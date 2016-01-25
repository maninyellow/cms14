package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.ListOnlineUsersVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询在线用户返回实体
 * @author wangbinyu <p />
 * Create at 2013 下午2:08:46
 */
public class ListOnlineUsersDTO extends BaseDTO {
	private List<ListOnlineUsersVO> userList = new ArrayList<ListOnlineUsersVO>();
	private String totalCount;

	public List<ListOnlineUsersVO> getUserList() {
		return userList;
	}

	public void setUserList(List<ListOnlineUsersVO> userList) {
		this.userList = userList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
