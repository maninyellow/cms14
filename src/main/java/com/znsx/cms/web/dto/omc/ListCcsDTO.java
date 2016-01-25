package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListCcsVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询通信服务器列表接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午6:35:50
 */
public class ListCcsDTO extends BaseDTO {
	private List<ListCcsVO> ccsList;

	public List<ListCcsVO> getCcsList() {
		return ccsList;
	}

	public void setCcsList(List<ListCcsVO> ccsList) {
		this.ccsList = ccsList;
	}
}
