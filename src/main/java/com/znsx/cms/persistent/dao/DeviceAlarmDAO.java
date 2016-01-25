package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DeviceAlarm;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.DeviceAlarmNumberVO;

/**
 * 设备告警数据库接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午6:40:36
 */
public interface DeviceAlarmDAO extends BaseDAO<DeviceAlarm, String> {

	/**
	 * 
	 * 查询设备告警记录
	 * 
	 * @param organId
	 *            传入的机构ID
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param alarmType
	 *            报警类型
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            查询条数
	 * @param organIds
	 *            根据用户查询的当前机构以及子机构
	 * @return 设备告警记录集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:14:45
	 */
	public List<DeviceAlarm> listDeviceAlarm(String organId, String deviceName,
			String deviceType, String alarmType, Long startTime, Long endTime,
			Integer startIndex, Integer limit, String[] organIds);

	/**
	 * 
	 * 统计设备告警记录条数
	 * 
	 * @param organId
	 *            传入的机构ID
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param alarmType
	 *            报警类型
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organIds
	 *            根据用户查询的当前机构以及子机构
	 * @return 满足条件的告警记录总条数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:33:37
	 */
	public Integer countDeviceAlarm(String organId, String deviceName,
			String deviceType, String alarmType, Long startTime, Long endTime,
			String[] organIds);

	/**
	 * 统计设备报警数量
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
	 * @return 统计设备数量
	 */
	public Integer deviceAlarmTotalCount(String deviceName, String deviceType,
			String organId, Long beginTime, Long endTime, String alarmType);

	/**
	 * 查询设备告警列表
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
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param alarmType
	 *            告警类型
	 * @return 告警列表
	 */
	public List<DeviceAlarm> listDeviceAlarmByOrganId(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			Integer startIndex, Integer limit, String alarmType);

	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的设备报警信息列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-16 下午2:07:29
	 */
	public void batchInsert(List<DeviceAlarm> list) throws BusinessException;

	/**
	 * 
	 * 根据sn查询历史报警列表
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 历史报警列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-5-06 上午9:58:18
	 */
	public List<DeviceAlarm> listDeviceAlarmBySN(String standardNumber,
			Long beginTime, Long endTime, Integer startIndex, Integer limit);

	/**
	 * 
	 * 统计告警数量
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午2:22:00
	 */
	public Integer deviceHistoryTotalCount(String standardNumber,
			Long beginTime, Long endTime);

	/**
	 * 
	 * 根据sn分组统计设备告警次数
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 告警次数和sn列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:25:50
	 */
	public List<DeviceAlarmNumberVO> listDeviceAlarmNumber(Long beginTime,
			Long endTime);

	/**
	 * 
	 * 查询告警历史表确认状态为null的告警
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
	 *            需要查询条数
	 * @param alarmType
	 *            告警类型
	 * @return 告警历史列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-10 上午11:57:59
	 */
	public List<DeviceAlarm> listFlagNull(String deviceName, String deviceType,
			String organId, Long beginTime, Long endTime, Integer startIndex,
			Integer limit, String alarmType);

	/**
	 * 
	 * 统计告警历史表确认为null的条数
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
	 *            报警类型
	 * @return 条数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-10 下午2:03:57
	 */
	public Integer deviceAlarmTotalFlagNullCount(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			String alarmType);

	/**
	 * 
	 * 条件查询告警历史数量
	 * 
	 * @param deviceId
	 *            设备ID
	 * @param deviceType
	 *            设备类型
	 * @param organs
	 *            机构ID数组
	 * @param alarmType
	 *            报警类型
	 * @return 告警历史数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-17 下午3:21:45
	 */
	public Integer deviceAlarmTotalFlagNullCount(String deviceId,
			String deviceType, String[] organs, String alarmType);

	/**
	 * 
	 * 查询历史报警列表
	 * 
	 * @param deviceId
	 *            设备id
	 * @param deviceType
	 *            设备类型
	 * @param organs
	 *            机构id
	 * @param alarmType
	 *            报警类型
	 * @return 历史报警列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-18 上午9:46:25
	 */
	public List<DeviceAlarm> listFlagNull(String deviceId, String deviceType,
			String[] organs, String alarmType);

	/**
	 * 
	 * 条件统计设备报警历史数量
	 * 
	 * @param organs
	 *            机构id
	 * @param deviceId
	 *            设备id
	 * @param deviceType
	 *            设备类型
	 * @param alarmType
	 *            报警类型
	 * @param type
	 *            是否确认类型 0:已确认 1:未确认 null:全部
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 报警历史数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-18 上午11:06:06
	 */
	public Integer deviceHistoryTotalCount(String[] organs, String deviceId,
			String deviceType, String alarmType, String type, Long beginTime,
			Long endTime);

	/**
	 * 
	 * 条件统计设备报警历史列表
	 * 
	 * @param organs
	 *            机构id
	 * @param deviceId
	 *            设备id
	 * @param deviceType
	 *            设备类型
	 * @param alarmType
	 *            报警类型
	 * @param type
	 *            是否确认类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 报警历史列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-18 上午11:27:01
	 */
	public List<DeviceAlarm> listDeviceAlarmHistory(String[] organs,
			String deviceId, String deviceType, String alarmType, String type,
			Long beginTime, Long endTime, Integer startIndex, Integer limit);
}
