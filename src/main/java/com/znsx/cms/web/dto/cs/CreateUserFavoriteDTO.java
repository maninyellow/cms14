package com.znsx.cms.web.dto.cs;

import com.znsx.cms.service.model.CreateUserFavoriteVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 创建播放方案接口返回对象
 * @author wangbinyu <p />
 * Create at 2013 下午6:47:46
 */
public class CreateUserFavoriteDTO extends BaseDTO{
	private CreateUserFavoriteVO favoriteVO;

	public CreateUserFavoriteVO getFavoriteVO() {
		return favoriteVO;
	}

	public void setFavoriteVO(CreateUserFavoriteVO favoriteVO) {
		this.favoriteVO = favoriteVO;
	}

	
}
