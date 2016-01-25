package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.RoadDeviceStatusVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListRoadDeviceStatusDTO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-8 下午5:14:52
 */
public class ListRoadDeviceStatusDTO extends BaseDTO {
	private String totalCount;
	private List<RoadDeviceStatusVO> items;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<RoadDeviceStatusVO> getItems() {
		return items;
	}

	public void setItems(List<RoadDeviceStatusVO> items) {
		this.items = items;
	}

}
