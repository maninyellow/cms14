package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.DeviceAlarmVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * DeviceAlarmDTO
 * @author wangbinyu <p />
 * Create at 2015 下午5:59:01
 */
public class DeviceAlarmDTO extends BaseDTO {
	private List<DeviceAlarmVO> deviceOnlineHistory;
	private String totalCount;

	public List<DeviceAlarmVO> getDeviceOnlineHistory() {
		return deviceOnlineHistory;
	}

	public void setDeviceOnlineHistory(List<DeviceAlarmVO> deviceOnlineHistory) {
		this.deviceOnlineHistory = deviceOnlineHistory;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
