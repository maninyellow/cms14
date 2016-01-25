package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.UnitWareHouseVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetWareHouseDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午6:18:07
 */
public class GetWareHouseDTO extends BaseDTO {
	private UnitWareHouseVO unit;

	public UnitWareHouseVO getUnit() {
		return unit;
	}

	public void setUnit(UnitWareHouseVO unit) {
		this.unit = unit;
	}
}
