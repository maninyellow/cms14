package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.UserFavorite;
import com.znsx.cms.service.model.CameraVO;

/**
 * 用户收藏夹数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:11:06
 */
public interface UserFavoriteDAO extends BaseDAO<UserFavorite, String> {
	/**
	 * 删除用户的收藏夹
	 * 
	 * @param userId
	 *            用户ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午11:43:27
	 */
	public void deleteByUser(String userId);

	/**
	 * 查询收藏夹中的摄像头列表
	 * 
	 * @param favoriteId
	 *            收藏夹ID
	 * @return 摄像头列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-26 下午4:03:41
	 */
	public List<CameraVO> listFavoriteCamera(String favoriteId);
}
