package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListMssVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询流媒体服务器列表接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午11:21:18
 */
public class ListMssDTO extends BaseDTO {
	private List<ListMssVO> mssList;

	public List<ListMssVO> getMssList() {
		return mssList;
	}

	public void setMssList(List<ListMssVO> mssList) {
		this.mssList = mssList;
	}

}
