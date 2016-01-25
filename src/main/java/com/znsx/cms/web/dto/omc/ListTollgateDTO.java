package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetTollgateVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListTollgateDTO
 * @author wangbinyu <p />
 * Create at 2014 上午10:00:46
 */
public class ListTollgateDTO extends BaseDTO {
	private List<GetTollgateVO> tollgateList;
	private String totalCount;
	
	public List<GetTollgateVO> getTollgateList() {
		return tollgateList;
	}

	public void setTollgateList(List<GetTollgateVO> tollgateList) {
		this.tollgateList = tollgateList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	
}
