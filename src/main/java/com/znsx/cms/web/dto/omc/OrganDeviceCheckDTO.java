package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.OrganDeviceCheck;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * OrganDeviceCheckDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午3:14:57
 */
public class OrganDeviceCheckDTO extends BaseDTO {
	private List<OrganDeviceCheck> deviceOnlineList;
	private String totalCount;

	public List<OrganDeviceCheck> getDeviceOnlineList() {
		return deviceOnlineList;
	}

	public void setDeviceOnlineList(List<OrganDeviceCheck> deviceOnlineList) {
		this.deviceOnlineList = deviceOnlineList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
