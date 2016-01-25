package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasGps;

/**
 * DasGpsDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-31 上午11:53:06
 */
public interface DasGpsDAO extends BaseDAO<DasGps, String> {

	/**
	 * 批量插入
	 * 
	 * @param listGpsHistory
	 *            GPS采集数据列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-31 下午3:02:55
	 */
	public void batchInsert(List<DasGps> listGpsHistory);
}
