package com.znsx.cms.persistent.dao;

import com.znsx.cms.persistent.model.StakeNumberLib;

/**
 * 桩号坐标映射对象数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-24 下午5:45:18
 */
public interface StakeNumberLibDAO extends BaseDAO<StakeNumberLib, String> {
	/**
	 * 根据桩号和机构ID查找坐标
	 * 
	 * @param stakeNumber
	 *            桩号
	 * @param organId
	 *            机构ID
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-24 下午5:52:04
	 */
	public StakeNumberLib findByStakeNumber(String stakeNumber, String organId);
}
