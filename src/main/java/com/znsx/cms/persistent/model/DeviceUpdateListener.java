package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 设备更新时间监听实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午6:08:37
 */
public class DeviceUpdateListener implements Serializable {
	private static final long serialVersionUID = 1087556503304665821L;
	private String id;
	private Long updateTime;
	private Long crsUpdateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCrsUpdateTime() {
		return crsUpdateTime;
	}

	public void setCrsUpdateTime(Long crsUpdateTime) {
		this.crsUpdateTime = crsUpdateTime;
	}

}
