package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetBridgeDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListBridgeDetectorDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:10:18
 */
public class ListBridgeDetectorDTO extends BaseDTO {
	private List<GetBridgeDetectorVO> bridgeDetectorList;

	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<GetBridgeDetectorVO> getBridgeDetectorList() {
		return bridgeDetectorList;
	}

	public void setBridgeDetectorList(
			List<GetBridgeDetectorVO> bridgeDetectorList) {
		this.bridgeDetectorList = bridgeDetectorList;
	}
}
