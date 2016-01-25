package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListStoreDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:35:16
 */
public class ListStoreDTO extends BaseDTO {
	private List<StoreVO> list;
	private String totalCount;

	public List<StoreVO> getList() {
		return list;
	}

	public void setList(List<StoreVO> list) {
		this.list = list;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
