package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetWindSpeedVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 风速风向检测器列表接口实体类
 * @author wangbinyu <p />
 * Create at 2013 上午9:59:50
 */
public class ListWindSpeedDTO extends BaseDTO {
	private List<GetWindSpeedVO> windSpeedList;
	private String totalCount;

	public List<GetWindSpeedVO> getWindSpeedList() {
		return windSpeedList;
	}

	public void setWindSpeedList(List<GetWindSpeedVO> windSpeedList) {
		this.windSpeedList = windSpeedList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
