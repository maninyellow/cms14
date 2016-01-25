package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetViDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListViDetectorDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:39:30
 */
public class ListViDetectorDTO extends BaseDTO {
	private List<GetViDetectorVO> viDetectorList;

	private String totalCount;

	public List<GetViDetectorVO> getViDetectorList() {
		return viDetectorList;
	}

	public void setViDetectorList(List<GetViDetectorVO> viDetectorList) {
		this.viDetectorList = viDetectorList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
