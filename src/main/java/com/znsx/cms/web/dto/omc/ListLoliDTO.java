package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetLoliVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 光照强度检测器列表接口实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:53:33
 */
public class ListLoliDTO extends BaseDTO {
	private List<GetLoliVO> loliList;
	private String totalCount;

	public List<GetLoliVO> getLoliList() {
		return loliList;
	}

	public void setLoliList(List<GetLoliVO> loliList) {
		this.loliList = loliList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
