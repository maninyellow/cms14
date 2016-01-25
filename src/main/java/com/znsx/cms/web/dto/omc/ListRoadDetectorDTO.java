package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetRoadDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListRoadDetectorDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:34:06
 */
public class ListRoadDetectorDTO extends BaseDTO {
	private List<GetRoadDetectorVO> roadDetectorList;

	private String totalCount;

	public List<GetRoadDetectorVO> getRoadDetectorList() {
		return roadDetectorList;
	}

	public void setRoadDetectorList(List<GetRoadDetectorVO> roadDetectorList) {
		this.roadDetectorList = roadDetectorList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
