package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetServiceZoneVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetServiceZoneDTO
 * @author wangbinyu <p />
 * Create at 2014 下午6:02:54
 */
public class GetServiceZoneDTO extends BaseDTO {
	private GetServiceZoneVO serviceZone;

	public GetServiceZoneVO getServiceZone() {
		return serviceZone;
	}

	public void setServiceZone(GetServiceZoneVO serviceZone) {
		this.serviceZone = serviceZone;
	}
}
