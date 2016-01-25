package com.znsx.cms.service.model;

import java.util.List;

/**
 * ListDwsMonitorVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:26:30
 */
public class ListDwsMonitorVO {
	private ListDwsVO dws;
	private List<MonitorVO> listMonitor;

	public ListDwsVO getDws() {
		return dws;
	}

	public void setDws(ListDwsVO dws) {
		this.dws = dws;
	}

	public List<MonitorVO> getListMonitor() {
		return listMonitor;
	}

	public void setListMonitor(List<MonitorVO> listMonitor) {
		this.listMonitor = listMonitor;
	}
}
