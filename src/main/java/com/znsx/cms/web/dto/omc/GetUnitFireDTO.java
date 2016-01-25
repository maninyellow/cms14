package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.UnitFireVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetUnitFireDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:00:47
 */
public class GetUnitFireDTO extends BaseDTO {
	private UnitFireVO unit;

	public UnitFireVO getUnit() {
		return unit;
	}

	public void setUnit(UnitFireVO unit) {
		this.unit = unit;
	}

}
