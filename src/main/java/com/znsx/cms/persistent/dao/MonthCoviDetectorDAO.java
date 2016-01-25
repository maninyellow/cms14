package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.MonthCoviDetector;

/**
 * MonthCoviDetectorDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-9-7 下午2:51:53
 */
public interface MonthCoviDetectorDAO extends
		BaseDAO<MonthCoviDetector, String> {
	/**
	 * 获取最近的记录
	 * 
	 * @return 最近的记录或空
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-31 下午7:16:14
	 */
	public MonthCoviDetector getLastRecord();

	/**
	 * 批量写入
	 * 
	 * @param list
	 *            要入库的对象集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-31 下午7:16:42
	 */
	public void batchInsert(List<MonthCoviDetector> list);

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
	 * @return 检测器采集归并数据列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-31 下午7:16:59
	 */
	public List<MonthCoviDetector> list(String sn, Long beginTime,
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
	 *         Create at 2015-8-31 下午7:17:22
	 */
	public int count(String sn, Long beginTime, Long endTime);
}
