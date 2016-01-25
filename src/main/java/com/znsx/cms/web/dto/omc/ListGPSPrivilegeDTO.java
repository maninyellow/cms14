package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListGPSPrivilegeDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午3:22:48
 */
public class ListGPSPrivilegeDTO extends BaseDTO {
	private String totalCount;
	private List<DevicePermissionVO> gpsDevices;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<DevicePermissionVO> getGpsDevices() {
		return gpsDevices;
	}

	public void setGpsDevices(List<DevicePermissionVO> gpsDevices) {
		this.gpsDevices = gpsDevices;
	}

}
