package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetLoliVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取光照强度检测器返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:32:16
 */
public class GetLoliDTO extends BaseDTO {
	private GetLoliVO loli;

	public GetLoliVO getLoli() {
		return loli;
	}

	public void setLoli(GetLoliVO loli) {
		this.loli = loli;
	}
}
