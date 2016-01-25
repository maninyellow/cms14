package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetPushButtonVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取手动报警器返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午9:37:51
 */
public class GetPushButtonDTO extends BaseDTO {
	private GetPushButtonVO pushButton;

	public GetPushButtonVO getPushButton() {
		return pushButton;
	}

	public void setPushButton(GetPushButtonVO pushButton) {
		this.pushButton = pushButton;
	}
}
