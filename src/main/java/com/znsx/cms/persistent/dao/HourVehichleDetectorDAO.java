package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.HourVehichleDetector;

/**
 * HourVehichleDetectorDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-13 上午10:40:19
 */
public interface HourVehichleDetectorDAO extends
		BaseDAO<HourVehichleDetector, String> {
	/**
	 * 获取最近的记录
	 * 
	 * @return 最近的记录或空
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-14 上午11:45:10
	 */
	public HourVehichleDetector getLastRecord();

	/**
	 * 批量写入
	 * 
	 * @param list
	 *            要入库的对象集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-14 上午11:45:37
	 */
	public void batchInsert(List<HourVehichleDetector> list);

	/**
	 * 列表查询
	 * 
	 * @param sn
	 *            设备SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 车检器采集归并数据列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-14 下午4:28:02
	 */
	public List<HourVehichleDetector> list(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * 统计满足条件的记录数
	 * 
	 * @param sn
	 *            设备SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-14 下午4:29:20
	 */
	public int count(String sn, Long beginTime, Long endTime);
}
