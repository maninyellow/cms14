package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.PlatformServerVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询所有的平台服务器列表接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:20:49
 */
public class ListPlatformServerDTO extends BaseDTO {
	private List<PlatformServerVO> platformServerList;
	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<PlatformServerVO> getPlatformServerList() {
		return platformServerList;
	}

	public void setPlatformServerList(List<PlatformServerVO> platformServerList) {
		this.platformServerList = platformServerList;
	}

}
