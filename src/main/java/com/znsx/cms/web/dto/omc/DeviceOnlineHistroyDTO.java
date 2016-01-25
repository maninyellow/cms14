package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.DeviceOnlineHistroyVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * DeviceOnlineHistroyDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:57:47
 */
public class DeviceOnlineHistroyDTO extends BaseDTO {
	private List<DeviceOnlineHistroyVO> deviceOnlineHistory;
	private String totalCount;

	public List<DeviceOnlineHistroyVO> getDeviceOnlineHistory() {
		return deviceOnlineHistory;
	}

	public void setDeviceOnlineHistory(
			List<DeviceOnlineHistroyVO> deviceOnlineHistory) {
		this.deviceOnlineHistory = deviceOnlineHistory;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
