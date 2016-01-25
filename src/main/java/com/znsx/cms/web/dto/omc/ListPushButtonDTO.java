package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetPushButtonVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据机构手动报警按钮列表接口实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午9:45:53
 */
public class ListPushButtonDTO extends BaseDTO {
	private List<GetPushButtonVO> pushButtonList;

	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<GetPushButtonVO> getPushButtonList() {
		return pushButtonList;
	}

	public void setPushButtonList(List<GetPushButtonVO> pushButtonList) {
		this.pushButtonList = pushButtonList;
	}
}
