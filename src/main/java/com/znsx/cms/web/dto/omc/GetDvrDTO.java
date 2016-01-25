package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetDvrVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询视频服务器接口返回实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:32:48
 */
public class GetDvrDTO extends BaseDTO {
	private GetDvrVO dvr = new GetDvrVO();

	public GetDvrVO getDvr() {
		return dvr;
	}

	public void setDvr(GetDvrVO dvr) {
		this.dvr = dvr;
	}
}
