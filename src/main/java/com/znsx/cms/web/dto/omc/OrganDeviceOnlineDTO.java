package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.OrganDeviceOnline;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * OrganDeviceOnlineDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:56:48
 */
public class OrganDeviceOnlineDTO extends BaseDTO {
	private List<OrganDeviceOnline> deviceOnlineList;

	private String totalCount;

	public List<OrganDeviceOnline> getDeviceOnlineList() {
		return deviceOnlineList;
	}

	public void setDeviceOnlineList(List<OrganDeviceOnline> deviceOnlineList) {
		this.deviceOnlineList = deviceOnlineList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
