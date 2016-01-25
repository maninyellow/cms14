package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.GetOrganVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListOrganByParentIdDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:21:13
 */
public class ListOrganByParentIdDTO extends BaseDTO {
	private List<GetOrganVO> organList = new ArrayList<GetOrganVO>();

	public List<GetOrganVO> getOrganList() {
		return organList;
	}

	public void setOrganList(List<GetOrganVO> organList) {
		this.organList = organList;
	}

}
