package com.znsx.cms.web.dto.ccs;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取设备最后更新时间接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午10:19:09
 */
public class GetDeviceUpdateTimeDTO extends BaseDTO {
	private String updateTime;
	private String crsUpdateTime;

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCrsUpdateTime() {
		return crsUpdateTime;
	}

	public void setCrsUpdateTime(String crsUpdateTime) {
		this.crsUpdateTime = crsUpdateTime;
	}

}
