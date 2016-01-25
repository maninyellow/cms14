package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListEnsVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询事件服务器列表接口返回对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午2:25:57
 */
public class ListEnsDTO extends BaseDTO {
	private List<ListEnsVO> ensList;

	public List<ListEnsVO> getEnsList() {
		return ensList;
	}

	public void setEnsList(List<ListEnsVO> ensList) {
		this.ensList = ensList;
	}
}
