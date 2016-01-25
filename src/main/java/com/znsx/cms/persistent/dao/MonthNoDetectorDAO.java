package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.MonthNoDetector;

/**
 * MonthNoDetectorDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午3:33:48
 */
public interface MonthNoDetectorDAO extends BaseDAO<MonthNoDetector, String> {

	/**
	 * 获取最后一次对象的时间
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午3:36:06
	 */
	public MonthNoDetector getLastRecord();

	/**
	 * 
	 * 批量插入数据
	 * 
	 * @param list
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午3:43:35
	 */
	public void batchInsert(List<MonthNoDetector> list);

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
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午5:14:52
	 */
	public int count(String sn, Long beginTime, Long endTime);

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
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:20:12
	 */
	public List<MonthNoDetector> list(String sn, Long beginTime, Long endTime,
			Integer start, Integer limit);

}
