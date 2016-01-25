package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.UnitVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetUnitDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:16:40
 */
public class GetUnitDTO extends BaseDTO {
	private UnitVO unit;

	public UnitVO getUnit() {
		return unit;
	}

	public void setUnit(UnitVO unit) {
		this.unit = unit;
	}

}
