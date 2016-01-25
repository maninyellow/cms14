package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.DisplayWallVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据机构查询电视墙列表返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午2:39:30
 */
public class ListWallDTO extends BaseDTO {
	private List<DisplayWallVO> displayWalls = new ArrayList<DisplayWallVO>();
	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<DisplayWallVO> getDisplayWalls() {
		return displayWalls;
	}

	public void setDisplayWalls(List<DisplayWallVO> displayWalls) {
		this.displayWalls = displayWalls;
	}

}
