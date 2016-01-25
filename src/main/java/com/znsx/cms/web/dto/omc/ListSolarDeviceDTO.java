package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListSolarDeviceDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:51:10
 */
public class ListSolarDeviceDTO extends BaseDTO {
	private String totalCount;
	private List<DevicePermissionVO> solarDevices;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<DevicePermissionVO> getSolarDevices() {
		return solarDevices;
	}

	public void setSolarDevices(List<DevicePermissionVO> solarDevices) {
		this.solarDevices = solarDevices;
	}

}
