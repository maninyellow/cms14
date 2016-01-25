package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetOrganRoadVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetOrganRoadDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:52:46
 */
public class GetOrganRoadDTO extends BaseDTO {
	private GetOrganRoadVO organ;

	public GetOrganRoadVO getOrgan() {
		return organ;
	}

	public void setOrgan(GetOrganRoadVO organ) {
		this.organ = organ;
	}

}
