package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetOrganTollgateVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetOrganTollgateDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:15:31
 */
public class GetOrganTollgateDTO extends BaseDTO {
	private GetOrganTollgateVO organ;

	public GetOrganTollgateVO getOrgan() {
		return organ;
	}

	public void setOrgan(GetOrganTollgateVO organ) {
		this.organ = organ;
	}

}
