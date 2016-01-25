package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Playlist;

/**
 * 播放方案数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 上午10:51:55
 */
public interface PlaylistDAO extends BaseDAO<Playlist, String> {
	public List<Playlist> listByOrgan(String organId);

	/**
	 * 查询收藏夹下的播放方案
	 * 
	 * @param folderId
	 *            收藏夹ID
	 * @param type
	 *            情报板子类型
	 * @return 收藏夹下的播放方案列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-14 下午1:54:15
	 */
	public List<Playlist> listPlaylist(String folderId, Short type);
}
