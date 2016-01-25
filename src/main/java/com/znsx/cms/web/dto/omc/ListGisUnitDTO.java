package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.WfsRoadAdminVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListGisUnitDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:12:47
 */
public class ListGisUnitDTO extends BaseDTO {
	private List<WfsRoadAdminVO> gisUnit;

	public List<WfsRoadAdminVO> getGisUnit() {
		return gisUnit;
	}

	public void setGisUnit(List<WfsRoadAdminVO> gisUnit) {
		this.gisUnit = gisUnit;
	}

}
