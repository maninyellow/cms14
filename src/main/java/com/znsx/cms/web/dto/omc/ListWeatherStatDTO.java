package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetWeatherStatVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 气象检测器列表接口实体类
 * @author wangbinyu <p />
 * Create at 2013 下午1:44:05
 */
public class ListWeatherStatDTO extends BaseDTO {
	private List<GetWeatherStatVO> weatherStatList;
	private String totalCount;

	public List<GetWeatherStatVO> getWeatherStatList() {
		return weatherStatList;
	}

	public void setWeatherStatList(List<GetWeatherStatVO> weatherStatList) {
		this.weatherStatList = weatherStatList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
