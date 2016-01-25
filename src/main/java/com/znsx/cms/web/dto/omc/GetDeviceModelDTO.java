package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.DeviceModelVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据ID查询厂商设备型号接口返回实体
 * @author wangbinyu <p />
 * Create at 2013 下午1:52:32
 */
public class GetDeviceModelDTO extends BaseDTO {
	private DeviceModelVO deviceModel = new DeviceModelVO();

	public DeviceModelVO getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(DeviceModelVO deviceModel) {
		this.deviceModel = deviceModel;
	}
}
