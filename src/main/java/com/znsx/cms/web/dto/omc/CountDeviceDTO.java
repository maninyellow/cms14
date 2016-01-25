package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.CountDataTypeVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 统计设备接口返回实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:31:21
 */
public class CountDeviceDTO extends BaseDTO {

	private List<CountDataTypeVO> deviceCount;

	private String[] organName;

	public List<CountDataTypeVO> getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(List<CountDataTypeVO> deviceCount) {
		this.deviceCount = deviceCount;
	}

	public String[] getOrganName() {
		return organName;
	}

	public void setOrganName(String[] organName) {
		this.organName = organName;
	}

}
