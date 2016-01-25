/**
 * 
 */
package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetOrganBridgeVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * @author znsx
 * 
 */
public class GetOrganBridgeDTO extends BaseDTO {
	private GetOrganBridgeVO organ;

	public GetOrganBridgeVO getOrgan() {
		return organ;
	}

	public void setOrgan(GetOrganBridgeVO organ) {
		this.organ = organ;
	}

}
