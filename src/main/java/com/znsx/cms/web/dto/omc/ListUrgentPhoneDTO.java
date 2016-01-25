package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetUrgentPhoneVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListUrgentPhoneDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:59:15
 */
public class ListUrgentPhoneDTO extends BaseDTO {
	private List<GetUrgentPhoneVO> urgentPhoneList;

	private String totalCount;

	public List<GetUrgentPhoneVO> getUrgentPhoneList() {
		return urgentPhoneList;
	}

	public void setUrgentPhoneList(List<GetUrgentPhoneVO> urgentPhoneList) {
		this.urgentPhoneList = urgentPhoneList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
