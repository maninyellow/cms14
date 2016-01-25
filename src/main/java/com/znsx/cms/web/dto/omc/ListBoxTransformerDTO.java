package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.GetBoxTransformerVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListBoxTransformerDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:47:39
 */
public class ListBoxTransformerDTO extends BaseDTO {
	private List<GetBoxTransformerVO> boxTransformerList;

	private String totalCount;

	public List<GetBoxTransformerVO> getBoxTransformerList() {
		return boxTransformerList;
	}

	public void setBoxTransformerList(
			List<GetBoxTransformerVO> boxTransformerList) {
		this.boxTransformerList = boxTransformerList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
