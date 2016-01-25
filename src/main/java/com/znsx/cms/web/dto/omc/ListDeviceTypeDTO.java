package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.persistent.model.TypeDef;
import com.znsx.cms.service.model.GetTypeDefVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListDeviceTypeDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:13:52
 */
public class ListDeviceTypeDTO extends BaseDTO {
	private List<GetTypeDefVO> typeDefs;

	public List<GetTypeDefVO> getTypeDefs() {
		return typeDefs;
	}

	public void setTypeDefs(List<GetTypeDefVO> typeDefs) {
		this.typeDefs = typeDefs;
	}

}
