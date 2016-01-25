package com.znsx.cms.web.dto.cs;

import com.znsx.cms.service.model.CreatePlaySchemeVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 创建播放方案接口返回对象
 * 
 * @author wangbinyu
 *         <p />
 * 
 *         Create at 2013 下午4:48:22
 */
public class CreatePlaySchemeDTO extends BaseDTO {
	private CreatePlaySchemeVO playVO;

	public CreatePlaySchemeVO getPlayVO() {
		return playVO;
	}

	public void setPlayVO(CreatePlaySchemeVO playVO) {
		this.playVO = playVO;
	}

}
