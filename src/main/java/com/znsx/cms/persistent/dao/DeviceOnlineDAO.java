package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DeviceOnline;

/**
 * DeviceOnlineDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-15 下午2:03:28
 */
public interface DeviceOnlineDAO extends BaseDAO<DeviceOnline, String> {
	/**
	 * 条件查询设备在线历史记录
	 * 
	 * @param standardNumber
	 *            设备SN
	 * @param begin
	 *            统计开始时间
	 * @param end
	 *            统计结束时间
	 * @return 设备在线历史记录列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-28 下午5:10:44
	 */
	public List<DeviceOnline> listDeviceOnline(String[] standardNumber,
			Long begin, Long end);
	
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-29 上午9:41:03
	 */
	public void batchInsert(List<DeviceOnline> list);

	/**
	 * 
	 * 根据sn查询设备在线历史数量
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 设备在线历史数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:41:39
	 */
	public Integer deviceHistoryTotalCount(String standardNumber,
			Long beginTime, Long endTime);

}
