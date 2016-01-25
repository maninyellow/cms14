package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.ListPtsVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询协议转换服务器列表接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:34:57
 */
public class ListPtsDTO extends BaseDTO {
	private List<ListPtsVO> ptsList;

	public List<ListPtsVO> getPtsList() {
		return ptsList;
	}

	public void setPtsList(List<ListPtsVO> ptsList) {
		this.ptsList = ptsList;
	}

}
