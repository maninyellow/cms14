package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.UnitHospitalVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetUnitHospitalDTO
 * @author wangbinyu <p />
 * Create at 2014 下午3:01:32
 */
public class GetUnitHospitalDTO extends BaseDTO {
	private UnitHospitalVO unit;

	public UnitHospitalVO getUnit() {
		return unit;
	}

	public void setUnit(UnitHospitalVO unit) {
		this.unit = unit;
	}
}
