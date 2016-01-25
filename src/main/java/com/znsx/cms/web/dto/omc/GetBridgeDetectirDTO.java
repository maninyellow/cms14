package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetBridgeDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetBridgeDetectirDTI
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午1:51:06
 */
public class GetBridgeDetectirDTO extends BaseDTO {
	private GetBridgeDetectorVO bridgeDetector;

	public GetBridgeDetectorVO getBridgeDetector() {
		return bridgeDetector;
	}

	public void setBridgeDetector(GetBridgeDetectorVO bridgeDetector) {
		this.bridgeDetector = bridgeDetector;
	}

}
