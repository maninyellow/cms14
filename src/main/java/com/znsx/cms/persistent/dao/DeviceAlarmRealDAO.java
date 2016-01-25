package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DeviceAlarmReal;

/**
 * DeviceAlarmRealDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-29 上午10:27:21
 */
public interface DeviceAlarmRealDAO extends BaseDAO<DeviceAlarmReal, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-29 上午11:10:28
	 */
	public void batchInsert(List<DeviceAlarmReal> list);

	/**
	 * 加悲观锁读取，防止多线程情况下的脏数据操作
	 * 
	 * @return sv_device_alarm_real表中的全部数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-29 上午11:23:47
	 */
	public List<DeviceAlarmReal> listUseLock();

	/**
	 * 
	 * 条件查询告警历史数量
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param alarmType
	 *            告警类型
	 * @return 告警历史数量
	 * @author wanngbinyu
	 *         <p />
	 *         Create at 2015 下午4:27:16
	 */
	public Integer deviceAlarmRealTotalCount(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			String alarmType);

	/**
	 * 
	 * 根据条件查询告警历史列表
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @param alarmType
	 *            告警类型
	 * @return 历史告警列表s
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:30:27
	 */
	public List<DeviceAlarmReal> listDeviceAlarmReal(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			Integer startIndex, Integer limit, String alarmType);

	/**
	 * 
	 * 统计报警数量
	 * 
	 * @param deviceId
	 *            设备ID
	 * @param deviceType
	 *            设备类型
	 * @param organs
	 *            机构id
	 * @param alarmType
	 *            报警类型
	 * @return 报警数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-17 下午2:30:32
	 */
	public Integer deviceAlarmTotalCount(String deviceId, String deviceType,
			String[] organs, String alarmType);

	/**
	 * 
	 * 统计报警列表
	 * 
	 * @param deviceId
	 *            设备id
	 * @param deviceType
	 *            设备类型
	 * @param organs
	 *            机构id
	 * @param alarmType
	 *            报警类型
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-18 上午9:42:24
	 */
	public List<DeviceAlarmReal> listDeviceAlarmReal(String deviceId,
			String deviceType, String[] organs, String alarmType);

}
