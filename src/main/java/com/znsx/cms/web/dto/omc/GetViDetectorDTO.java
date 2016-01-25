package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetViDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetViDetectorDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:31:11
 */
public class GetViDetectorDTO extends BaseDTO {
	private GetViDetectorVO viDetector;

	public GetViDetectorVO getViDetector() {
		return viDetector;
	}

	public void setViDetector(GetViDetectorVO viDetector) {
		this.viDetector = viDetector;
	}
}
