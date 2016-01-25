package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetCameraVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据ID查询摄像头接口实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午6:03:06
 */
public class GetCameraDTO extends BaseDTO {
	private GetCameraVO camera = new GetCameraVO();

	public GetCameraVO getCamera() {
		return camera;
	}

	public void setCamera(GetCameraVO camera) {
		this.camera = camera;
	}

}
