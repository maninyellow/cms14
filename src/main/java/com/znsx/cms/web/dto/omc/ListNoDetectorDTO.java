package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetNoDetectorVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 氮氧化合物检测器列表接口实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午2:08:09
 */
public class ListNoDetectorDTO extends BaseDTO {
	private List<GetNoDetectorVO> noDetectorList;
	private String totalCount;

	public List<GetNoDetectorVO> getNoDetectorList() {
		return noDetectorList;
	}

	public void setNoDetectorList(List<GetNoDetectorVO> noDetectorList) {
		this.noDetectorList = noDetectorList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
