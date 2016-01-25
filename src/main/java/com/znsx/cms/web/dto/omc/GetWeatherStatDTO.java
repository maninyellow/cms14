package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetWeatherStatVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取气象检测器返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:09:55
 */
public class GetWeatherStatDTO extends BaseDTO {
	private GetWeatherStatVO weatherStat;

	public GetWeatherStatVO getWeatherStat() {
		return weatherStat;
	}

	public void setWeatherStat(GetWeatherStatVO weatherStat) {
		this.weatherStat = weatherStat;
	}

}
