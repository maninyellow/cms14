package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.MonitorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据电视墙ID查询视频输出列表接口返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午7:38:49
 */
public class ListMonitorDTO extends BaseDTO {
	private List<MonitorVO> monitors = new ArrayList<MonitorVO>();
	private String totalCount;

	public List<MonitorVO> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<MonitorVO> monitors) {
		this.monitors = monitors;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
