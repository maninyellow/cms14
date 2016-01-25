package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetWindSpeedVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取风速风向检测器返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午9:49:04
 */
public class GetWindSpeedDTO extends BaseDTO {
	private GetWindSpeedVO windSpeed;

	public GetWindSpeedVO getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(GetWindSpeedVO windSpeed) {
		this.windSpeed = windSpeed;
	}

}
