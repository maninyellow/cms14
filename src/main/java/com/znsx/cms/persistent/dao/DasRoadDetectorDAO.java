package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.DasRoadDetector;

/**
 * DasRoadDetectorDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午10:03:37
 */
public interface DasRoadDetectorDAO extends BaseDAO<DasRoadDetector, String> {

	/**
	 * 
	 * 批量插入历史表数据
	 * 
	 * @param listRdHistory
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:23:45
	 */
	public void batchInsert(List<DasRoadDetector> listRdHistory);

	/**
	 * 查询时间范围内的路面检测器采集数据
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 时间范围内的路面检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-2 上午9:37:25
	 */
	public List<DasRoadDetector> listDasRsd(Timestamp begin, Timestamp end,
			int start, int limit);
}
