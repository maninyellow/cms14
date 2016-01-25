package com.znsx.cms.web.dto.cs;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.DeviceModelVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询厂商设备型号接口返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:10:39
 */
public class ListDeviceModelDTO extends BaseDTO {
	private List<DeviceModelVO> listDeviceModel = new ArrayList<DeviceModelVO>();

	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<DeviceModelVO> getListDeviceModel() {
		return listDeviceModel;
	}

	public void setListDeviceModel(List<DeviceModelVO> listDeviceModel) {
		this.listDeviceModel = listDeviceModel;
	}
}
