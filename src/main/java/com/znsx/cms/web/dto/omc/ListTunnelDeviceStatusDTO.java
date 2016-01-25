package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.TunnelDeviceStatusVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListTunnelDeviceStatusDTO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-9 下午2:24:37
 */
public class ListTunnelDeviceStatusDTO extends BaseDTO {
	private String totalCount;
	private List<TunnelDeviceStatusVO> items;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<TunnelDeviceStatusVO> getItems() {
		return items;
	}

	public void setItems(List<TunnelDeviceStatusVO> items) {
		this.items = items;
	}

}
