package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 设备离线在线记录
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-15 下午1:58:05
 */
public class DeviceOnline implements Serializable {
	private static final long serialVersionUID = -3184935861010529019L;
	private String id;
	private String standardNumber;
	private Long onlineTime;
	private Long offlineTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public Long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Long getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Long offlineTime) {
		this.offlineTime = offlineTime;
	}

}
