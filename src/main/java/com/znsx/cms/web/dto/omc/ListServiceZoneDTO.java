package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetServiceZoneVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListServiceZoneDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午6:18:11
 */
public class ListServiceZoneDTO extends BaseDTO {
	private String totalCount;
	private List<GetServiceZoneVO> serviceZoneList;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<GetServiceZoneVO> getServiceZoneList() {
		return serviceZoneList;
	}

	public void setServiceZoneList(List<GetServiceZoneVO> serviceZoneList) {
		this.serviceZoneList = serviceZoneList;
	}

}
