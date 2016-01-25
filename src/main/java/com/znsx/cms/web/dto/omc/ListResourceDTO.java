package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * 应急物资返回列表
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:57:39
 */
public class ListResourceDTO extends BaseDTO {
	private List<ResourceVO> resourceList;
	private String totalCount;

	public List<ResourceVO> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<ResourceVO> resourceList) {
		this.resourceList = resourceList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
