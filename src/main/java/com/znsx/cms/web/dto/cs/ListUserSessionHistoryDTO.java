package com.znsx.cms.web.dto.cs;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.ListUserSessionHistoryVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListUserSessionHistoryDTO
 * @author wangbinyu <p />
 * Create at 2013 下午3:41:50
 */
public class ListUserSessionHistoryDTO extends BaseDTO {
	List<ListUserSessionHistoryVO> userSessionList = new ArrayList<ListUserSessionHistoryVO>();
	private String totalCount;

	public List<ListUserSessionHistoryVO> getUserSessionList() {
		return userSessionList;
	}

	public void setUserSessionList(
			List<ListUserSessionHistoryVO> userSessionList) {
		this.userSessionList = userSessionList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
