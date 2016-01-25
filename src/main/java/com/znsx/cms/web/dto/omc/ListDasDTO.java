package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListDasVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询数据采集服务器列表接口返回对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:12:54
 */
public class ListDasDTO extends BaseDTO {
	private List<ListDasVO> dasList;

	public List<ListDasVO> getDasList() {
		return dasList;
	}

	public void setDasList(List<ListDasVO> dasList) {
		this.dasList = dasList;
	}
}
