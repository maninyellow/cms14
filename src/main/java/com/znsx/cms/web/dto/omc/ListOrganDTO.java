package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetOrganVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据机构ID或者机构名称查询机构列表接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:22:46
 */
public class ListOrganDTO extends BaseDTO {
	private List<GetOrganVO> organList;
	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<GetOrganVO> getOrganList() {
		return organList;
	}

	public void setOrganList(List<GetOrganVO> organList) {
		this.organList = organList;
	}

}
