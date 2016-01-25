package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListGisVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListGisDTO
 * @author wangbinyu <p />
 * Create at 2014 下午3:54:32
 */
public class ListGisDTO extends BaseDTO {
	private List<ListGisVO> gisList;

	public List<ListGisVO> getGisList() {
		return gisList;
	}

	public void setGisList(List<ListGisVO> gisList) {
		this.gisList = gisList;
	}
}
