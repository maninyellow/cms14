package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetCoviVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取一氧化碳\能见度检测器返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:50:21
 */
public class GetCoviDTO extends BaseDTO {
	private GetCoviVO covi;

	public GetCoviVO getCovi() {
		return covi;
	}

	public void setCovi(GetCoviVO covi) {
		this.covi = covi;
	}
}
