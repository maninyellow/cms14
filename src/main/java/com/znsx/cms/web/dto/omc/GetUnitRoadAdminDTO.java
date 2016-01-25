package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.UnitRoadAdminVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetUnitRoadAdminDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:02:22
 */
public class GetUnitRoadAdminDTO extends BaseDTO {
	private UnitRoadAdminVO unit;

	public UnitRoadAdminVO getUnit() {
		return unit;
	}

	public void setUnit(UnitRoadAdminVO unit) {
		this.unit = unit;
	}

}
