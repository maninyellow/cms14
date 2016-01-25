package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetRoadMouthVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListRoadMouthDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:57:03
 */
public class ListRoadMouthDTO extends BaseDTO {
	private List<GetRoadMouthVO> roadMouthList;
	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<GetRoadMouthVO> getRoadMouthList() {
		return roadMouthList;
	}

	public void setRoadMouthList(List<GetRoadMouthVO> roadMouthList) {
		this.roadMouthList = roadMouthList;
	}
}
