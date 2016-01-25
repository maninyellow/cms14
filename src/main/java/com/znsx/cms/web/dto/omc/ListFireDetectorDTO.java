package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetFireDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据机构ID查询火灾检测器列表接口实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:12:35
 */
public class ListFireDetectorDTO extends BaseDTO {
	private List<GetFireDetectorVO> fireDetectorList;

	private String totalCount;

	public List<GetFireDetectorVO> getFireDetectorList() {
		return fireDetectorList;
	}

	public void setFireDetectorList(List<GetFireDetectorVO> fireDetectorList) {
		this.fireDetectorList = fireDetectorList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
