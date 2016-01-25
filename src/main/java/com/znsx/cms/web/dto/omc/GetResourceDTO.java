package com.znsx.cms.web.dto.omc;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetResourceDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:10:58
 */
public class GetResourceDTO extends BaseDTO {
	private ResourceVO resource;

	public ResourceVO getResource() {
		return resource;
	}

	public void setResource(ResourceVO resource) {
		this.resource = resource;
	}

}
