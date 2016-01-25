package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetRoadDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetRoadDetectorDTO
 * @author wangbinyu <p />
 * Create at 2014 上午11:19:33
 */
public class GetRoadDetectorDTO extends BaseDTO {
	private GetRoadDetectorVO roadDetector;

	public GetRoadDetectorVO getRoadDetector() {
		return roadDetector;
	}

	public void setRoadDetector(GetRoadDetectorVO roadDetector) {
		this.roadDetector = roadDetector;
	}
}
