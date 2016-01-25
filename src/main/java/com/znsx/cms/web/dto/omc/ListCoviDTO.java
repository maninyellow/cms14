package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetCoviVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据机构ID查询一氧化碳\能见度检测器列表接口实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午4:13:15
 */
public class ListCoviDTO extends BaseDTO {
	private List<GetCoviVO> coviList;

	public List<GetCoviVO> getCoviList() {
		return coviList;
	}

	public void setCoviList(List<GetCoviVO> coviList) {
		this.coviList = coviList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	private String totalCount;
}
