package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetOrganVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据机构ID查询机构接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:35:17
 */
public class GetOrganDTO extends BaseDTO {
	private GetOrganVO organ;

	public GetOrganVO getOrgan() {
		return organ;
	}

	public void setOrgan(GetOrganVO organ) {
		this.organ = organ;
	}

}
