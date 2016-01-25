package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * DeviceOnlineReal
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-17 上午10:25:45
 */
public class DeviceOnlineReal implements Serializable {
	private static final long serialVersionUID = -8618069847749800137L;
	private String id;
	private String standardNumber;
	private Short status;
	private Long updateTime;
	private Long onlineTime;

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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}

}
