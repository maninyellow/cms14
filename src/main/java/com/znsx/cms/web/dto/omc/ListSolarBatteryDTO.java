package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetSolarBatteryVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListSolarBatteryDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:35:23
 */
public class ListSolarBatteryDTO extends BaseDTO {
	private String totalCount;
	private List<GetSolarBatteryVO> solarBatteryList;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<GetSolarBatteryVO> getSolarBatteryList() {
		return solarBatteryList;
	}

	public void setSolarBatteryList(List<GetSolarBatteryVO> solarBatteryList) {
		this.solarBatteryList = solarBatteryList;
	}
}
