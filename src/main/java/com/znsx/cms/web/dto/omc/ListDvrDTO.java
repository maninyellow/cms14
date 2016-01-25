package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.DvrVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询视频服务器列表接口返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:56:47
 */
public class ListDvrDTO extends BaseDTO {
	private List<DvrVO> dvrList = new ArrayList<DvrVO>();

	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<DvrVO> getDvrList() {
		return dvrList;
	}

	public void setDvrList(List<DvrVO> dvrList) {
		this.dvrList = dvrList;
	}

}
