package com.znsx.cms.web.dto.cs;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.FavoriteVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询收藏夹返回对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午7:44:33
 */
public class ListUserFavoriteDTO extends BaseDTO{
	private List<FavoriteVO> listUserFavorite = new ArrayList<FavoriteVO>();

	public List<FavoriteVO> getListUserFavorite() {
		return listUserFavorite;
	}

	public void setListUserFavorite(List<FavoriteVO> listUserFavorite) {
		this.listUserFavorite = listUserFavorite;
	}

	
}
