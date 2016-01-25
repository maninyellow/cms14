package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListRssVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListRssDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午10:37:50
 */
public class ListRssDTO extends BaseDTO {
	private List<ListRssVO> rssList;

	public List<ListRssVO> getRssList() {
		return rssList;
	}

	public void setRssList(List<ListRssVO> rssList) {
		this.rssList = rssList;
	}

}
