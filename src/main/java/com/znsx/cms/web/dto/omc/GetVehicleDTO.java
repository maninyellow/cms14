package com.znsx.cms.web.dto.omc;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetVehicleDTO
 * @author wangbinyu <p />
 * Create at 2014 下午4:24:40
 */
public class GetVehicleDTO extends BaseDTO {
	private VehicleVO vehicle;

	public VehicleVO getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleVO vehicle) {
		this.vehicle = vehicle;
	}
}
