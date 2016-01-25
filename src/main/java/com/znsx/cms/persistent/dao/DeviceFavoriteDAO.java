package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DeviceFavorite;

/**
 * DeviceFavoriteDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-12 上午10:33:45
 */
public interface DeviceFavoriteDAO extends BaseDAO<DeviceFavorite, String> {
	/**
	 * 根据用户收藏夹删除
	 * 
	 * @param favoriteId
	 *            用户收藏夹ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-13 上午9:51:40
	 */
	public void deleteByFavoriteId(String favoriteId);

	/**
	 * 根据用户收藏夹查询列表
	 * 
	 * @param favoriteId
	 *            用户收藏夹ID
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-13 上午9:54:12
	 */
	public List<DeviceFavorite> listByFavoriteId(String favoriteId);

	/**
	 * 根据收藏夹ID和设备ID获取关联对象
	 * 
	 * @param favoriteId
	 *            收藏夹ID
	 * @param deviceId
	 *            设备ID
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-14 上午9:39:17
	 */
	public DeviceFavorite get(String favoriteId, String deviceId);
}
