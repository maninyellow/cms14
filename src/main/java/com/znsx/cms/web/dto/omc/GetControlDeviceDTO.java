package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetControlDeviceVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取控制设备返回实体
 * @author wangbinyu <p />
 * Create at 2013 下午3:19:07
 */
public class GetControlDeviceDTO extends BaseDTO {
	private GetControlDeviceVO controlDevice;

	public GetControlDeviceVO getControlDevice() {
		return controlDevice;
	}

	public void setControlDevice(GetControlDeviceVO controlDevice) {
		this.controlDevice = controlDevice;
	}
}
