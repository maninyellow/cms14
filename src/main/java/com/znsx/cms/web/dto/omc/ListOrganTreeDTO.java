package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.OrganVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 递归查询机构树接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午6:15:54
 */
public class ListOrganTreeDTO extends BaseDTO {
	private OrganVO organ;

	public OrganVO getOrgan() {
		return organ;
	}

	public void setOrgan(OrganVO organ) {
		this.organ = organ;
	}
}
