package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.PlatformServerVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据ID查询平台服务器接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:40:13
 */
public class GetPlatformServerDTO extends BaseDTO {
	private PlatformServerVO platformServer;

	public PlatformServerVO getPlatformServer() {
		return platformServer;
	}

	public void setPlatformServer(PlatformServerVO platformServer) {
		this.platformServer = platformServer;
	}

}
