package com.znsx.cms.web.dto.cs;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.ListDeviceAlarmVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询告警信息接口返回实体
 * @author wangbinyu <p />
 * Create at 2013 下午5:03:25
 */
public class ListDeviceAlarmDTO extends BaseDTO {
	private List<ListDeviceAlarmVO> deviceAlarm = new ArrayList<ListDeviceAlarmVO>();
	private String totalCount;

	public List<ListDeviceAlarmVO> getDeviceAlarm() {
		return deviceAlarm;
	}

	public void setDeviceAlarm(List<ListDeviceAlarmVO> deviceAlarm) {
		this.deviceAlarm = deviceAlarm;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
