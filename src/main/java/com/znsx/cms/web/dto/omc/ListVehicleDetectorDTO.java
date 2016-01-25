package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetVehicleDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 车辆检测器列表接口实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午4:01:03
 */
public class ListVehicleDetectorDTO extends BaseDTO {
	private List<GetVehicleDetectorVO> vehicleDetectorList;
	private String totalCount;

	public List<GetVehicleDetectorVO> getVehicleDetectorList() {
		return vehicleDetectorList;
	}

	public void setVehicleDetectorList(
			List<GetVehicleDetectorVO> vehicleDetectorList) {
		this.vehicleDetectorList = vehicleDetectorList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
