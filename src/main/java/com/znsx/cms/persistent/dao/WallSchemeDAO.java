package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.WallScheme;

/**
 * @author huangbuji
 *         <p />
 *         2014-11-18 下午3:20:55
 */
public interface WallSchemeDAO extends BaseDAO<WallScheme, String> {
	/**
	 * 查询电视墙控制方案列表
	 * 
	 * @author huangbuji
	 *         <p />
	 *         2014-11-18 下午8:24:48
	 */
	public List<WallScheme> listWallScheme(String userId, String organId);
}
