package com.znsx.cms.web.dto.omc;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetResourceUserDTO
 * @author wangbinyu <p />
 * Create at 2014 下午7:31:42
 */
public class GetResourceUserDTO extends BaseDTO {
	private ResourceUserVO resourceUser;

	public ResourceUserVO getResourceUser() {
		return resourceUser;
	}

	public void setResourceUser(ResourceUserVO resourceUser) {
		this.resourceUser = resourceUser;
	}
}
