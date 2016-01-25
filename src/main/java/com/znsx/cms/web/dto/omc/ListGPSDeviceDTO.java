package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.DeviceGPSVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListGPSDeviceDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午6:09:06
 */
public class ListGPSDeviceDTO extends BaseDTO {
	private List<DeviceGPSVO> gpsDeviceList;

	private String totalCount;

	public List<DeviceGPSVO> getGpsDeviceList() {
		return gpsDeviceList;
	}

	public void setGpsDeviceList(List<DeviceGPSVO> gpsDeviceList) {
		this.gpsDeviceList = gpsDeviceList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
