package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.ListOrganVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据机构ID查询机构以及子机构列表接口返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午9:48:41
 */
public class ListOrganByIdDTO extends BaseDTO {
	private List<ListOrganVO> listOrgan = new ArrayList<ListOrganVO>();

	public List<ListOrganVO> getListOrgan() {
		return listOrgan;
	}

	public void setListOrgan(List<ListOrganVO> listOrgan) {
		this.listOrgan = listOrgan;
	}

}
