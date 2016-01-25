package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.MonitorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据ID查询视频输出接口返回实体
 * @author wangbinyu <p />
 * Create at 2013 下午7:06:40
 */
public class GetMonitorDTO extends BaseDTO {
	private MonitorVO monitor;

	public MonitorVO getMonitor() {
		return monitor;
	}

	public void setMonitor(MonitorVO monitor) {
		this.monitor = monitor;
	}
}
