package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetControlDeviceVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 控制设备列表接口实体类
 * @author wangbinyu <p />
 * Create at 2013 下午3:36:02
 */
public class ListControlDeviceDTO extends BaseDTO {
	private List<GetControlDeviceVO> controlDeviceList;
	private String totalCount;

	public List<GetControlDeviceVO> getControlDeviceList() {
		return controlDeviceList;
	}

	public void setControlDeviceList(List<GetControlDeviceVO> controlDeviceList) {
		this.controlDeviceList = controlDeviceList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
