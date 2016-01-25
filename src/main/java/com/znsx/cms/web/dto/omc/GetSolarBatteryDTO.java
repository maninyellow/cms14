package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetSolarBatteryVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetSolarBatteryDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:17:31
 */
public class GetSolarBatteryDTO extends BaseDTO {
	private GetSolarBatteryVO solarBattery;

	public GetSolarBatteryVO getSolarBattery() {
		return solarBattery;
	}

	public void setSolarBattery(GetSolarBatteryVO solarBattery) {
		this.solarBattery = solarBattery;
	}
}
