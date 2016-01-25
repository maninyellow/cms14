package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Rss;

/**
 * RssDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-27 上午9:57:16
 */
public interface RssDAO extends BaseDAO<Rss, String> {

	/**
	 * 
	 * 根据名称查询状态服务器
	 * 
	 * @param name
	 *            服务器名称
	 * @return 状态服务器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-11 上午10:42:13
	 */
	public List<Rss> findRssByName(String name);

}
