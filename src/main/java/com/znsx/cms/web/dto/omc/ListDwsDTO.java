package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListDwsVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询电视墙服务器列表接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:07:05
 */
public class ListDwsDTO extends BaseDTO {
	private List<ListDwsVO> dwsList;

	public List<ListDwsVO> getDwsList() {
		return dwsList;
	}

	public void setDwsList(List<ListDwsVO> dwsList) {
		this.dwsList = dwsList;
	}

}
