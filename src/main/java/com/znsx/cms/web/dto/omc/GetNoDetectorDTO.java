package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetNoDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取氮氧化合物检测器返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午1:58:48
 */
public class GetNoDetectorDTO extends BaseDTO {
	private GetNoDetectorVO noDetector;

	public GetNoDetectorVO getNoDetector() {
		return noDetector;
	}

	public void setNoDetector(GetNoDetectorVO noDetector) {
		this.noDetector = noDetector;
	}
}
