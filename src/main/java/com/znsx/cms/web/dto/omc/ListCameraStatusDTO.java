package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.StatCameraStatusVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListCameraStatusDTO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-9 下午5:40:25
 */
public class ListCameraStatusDTO extends BaseDTO {
	private String totalCount;
	private List<StatCameraStatusVO> items;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<StatCameraStatusVO> getItems() {
		return items;
	}

	public void setItems(List<StatCameraStatusVO> items) {
		this.items = items;
	}

}
