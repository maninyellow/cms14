package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.DeviceGPSVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetGPSDeviceDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:44:34
 */
public class GetGPSDeviceDTO extends BaseDTO {
	private DeviceGPSVO gpsDevice;

	public DeviceGPSVO getGpsDevice() {
		return gpsDevice;
	}

	public void setGpsDevice(DeviceGPSVO gpsDevice) {
		this.gpsDevice = gpsDevice;
	}

}
