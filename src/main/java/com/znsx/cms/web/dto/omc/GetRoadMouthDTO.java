package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetRoadMouthVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetRoadMouthDTO
 * @author wangbinyu <p />
 * Create at 2014 下午5:39:12
 */
public class GetRoadMouthDTO extends BaseDTO {
	private GetRoadMouthVO roadMouth;

	public GetRoadMouthVO getRoadMouth() {
		return roadMouth;
	}

	public void setRoadMouth(GetRoadMouthVO roadMouth) {
		this.roadMouth = roadMouth;
	}
}
