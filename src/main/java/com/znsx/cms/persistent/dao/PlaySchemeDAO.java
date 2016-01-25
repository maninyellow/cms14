package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.PlayScheme;

/**
 * 播放方案数据库接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午2:15:42
 */
public interface PlaySchemeDAO extends BaseDAO<PlayScheme, String> {

	/**
	 * 
	 * 根据机构ID数组查询播放方案列表
	 * 
	 * @param organs
	 *            机构数组
	 * @return 播放方案列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015 下午6:00:17
	 */
	public List<PlayScheme> findByOrgans(String[] organs);

}
