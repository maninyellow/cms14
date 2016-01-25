package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListResourceUserDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午7:42:06
 */
public class ListResourceUserDTO extends BaseDTO {
	private List<ResourceUserVO> resourceUserList;
	private String totalCount;

	public List<ResourceUserVO> getResourceUserList() {
		return resourceUserList;
	}

	public void setResourceUserList(List<ResourceUserVO> resourceUserList) {
		this.resourceUserList = resourceUserList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
