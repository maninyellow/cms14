package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.GetOrganVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据机构名称模糊查询机构列表
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:44:54
 */
public class ListOrganByNameDTO extends BaseDTO {

	public List<GetOrganVO> getOrganList() {
		return organList;
	}

	public void setOrganList(List<GetOrganVO> organList) {
		this.organList = organList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	private List<GetOrganVO> organList = new ArrayList<GetOrganVO>();
	private String totalCount;
}
