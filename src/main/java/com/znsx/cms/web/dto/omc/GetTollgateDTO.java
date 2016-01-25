package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetTollgateVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetTollgateDTO
 * @author wangbinyu <p />
 * Create at 2014 上午9:48:47
 */
public class GetTollgateDTO extends BaseDTO {
	private GetTollgateVO tollgate;

	public GetTollgateVO getTollgate() {
		return tollgate;
	}

	public void setTollgate(GetTollgateVO tollgate) {
		this.tollgate = tollgate;
	}
}
