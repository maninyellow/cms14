package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListRmsVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询录像转发服务器列表接口返回对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午9:38:04
 */
public class ListRmsDTO extends BaseDTO {
	private List<ListRmsVO> rmsList;

	public List<ListRmsVO> getRmsList() {
		return rmsList;
	}

	public void setRmsList(List<ListRmsVO> rmsList) {
		this.rmsList = rmsList;
	}

}
