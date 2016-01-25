package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DayNoDetector;

/**
 * DayNoDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午2:39:08
 */
public interface DayNoDetectorDAO extends BaseDAO<DayNoDetector, String> {

	/**
	 * 获取最后时间对象
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午3:00:41
	 */
	public DayNoDetector getLastRecord();

	/**
	 * 批量创建no
	 * 
	 * @param list
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午3:13:31
	 */
	public void batchInsert(List<DayNoDetector> list);

	/**
	 * 资源列表
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
	 *         Create at 2015-9-11 下午3:37:28
	 */
	public List<DayNoDetector> list(String sn, Long beginTime, Long endTime,
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
	 *         Create at 2015-9-11 下午4:53:45
	 */
	public int count(String sn, Long beginTime, Long endTime);

}
