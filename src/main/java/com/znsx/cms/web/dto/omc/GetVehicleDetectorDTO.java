package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetVehicleDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取车辆检测器返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:31:14
 */
public class GetVehicleDetectorDTO extends BaseDTO {
	private GetVehicleDetectorVO vehicleDetector;

	public GetVehicleDetectorVO getVehicleDetector() {
		return vehicleDetector;
	}

	public void setVehicleDetector(GetVehicleDetectorVO vehicleDetector) {
		this.vehicleDetector = vehicleDetector;
	}

}
