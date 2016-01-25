package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListSrsVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询录音服务器列表接口返回对象
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 上午10:41:47
 */
public class ListSrsDTO extends BaseDTO {

	private List<ListSrsVO> srsList;

	public List<ListSrsVO> getSrsList() {
		return srsList;
	}

	public void setSrsList(List<ListSrsVO> srsList) {
		this.srsList = srsList;
	}

}
