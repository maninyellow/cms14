package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.HourNoDetector;

/**
 * HourNoDetectorDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午2:23:36
 */
public interface HourNoDetectorDAO extends BaseDAO<HourNoDetector, String> {

	/**
	 * 获取最后一次记录
	 * 
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-10 下午2:27:31
	 */
	public HourNoDetector getLastRecord();

	/**
	 * 
	 * 批量创建CMS库小时氮氧化物
	 * 
	 * @param list
	 *            氮氧化物集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-10 下午3:17:15
	 */
	public void batchInsert(List<HourNoDetector> list);

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
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午3:04:57
	 */
	public List<HourNoDetector> list(String sn, Long beginTime, Long endTime,
			int start, int limit);
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
	 *         Create at 2015-9-11 下午4:33:10
	 */
	public int countNoByHour(String sn, Long beginTime, Long endTime);

}
