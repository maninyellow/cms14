package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListCrsVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询中心存储服务器列表接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午10:41:47
 */
public class ListCrsDTO extends BaseDTO {

	private List<ListCrsVO> crsList;

	public List<ListCrsVO> getCrsList() {
		return crsList;
	}

	public void setCrsList(List<ListCrsVO> crsList) {
		this.crsList = crsList;
	}

}
