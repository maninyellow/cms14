package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.UnitVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListUnitDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:33:07
 */
public class ListUnitDTO extends BaseDTO {
	private List<UnitVO> unitlist;
	private String totalCount;

	public List<UnitVO> getUnitlist() {
		return unitlist;
	}

	public void setUnitlist(List<UnitVO> unitlist) {
		this.unitlist = unitlist;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
