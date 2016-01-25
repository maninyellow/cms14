package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.DisplayWallVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据ID查询电视墙返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午2:41:50
 */
public class GetWallDTO extends BaseDTO {
	private DisplayWallVO wall;

	public DisplayWallVO getWall() {
		return wall;
	}

	public void setWall(DisplayWallVO wall) {
		this.wall = wall;
	}

}
