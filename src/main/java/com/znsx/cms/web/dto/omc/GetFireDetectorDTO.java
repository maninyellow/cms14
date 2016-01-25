package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetFireDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取火灾检测器返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午5:24:19
 */
public class GetFireDetectorDTO extends BaseDTO {
	private GetFireDetectorVO fireDetector;

	public GetFireDetectorVO getFireDetector() {
		return fireDetector;
	}

	public void setFireDetector(GetFireDetectorVO fireDetector) {
		this.fireDetector = fireDetector;
	}

}
