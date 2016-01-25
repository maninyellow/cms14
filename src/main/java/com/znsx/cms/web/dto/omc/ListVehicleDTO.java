package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListVehicleDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:50:24
 */
public class ListVehicleDTO extends BaseDTO {
	private List<VehicleVO> vehicleList;
	private String totalCount;

	public List<VehicleVO> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<VehicleVO> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
