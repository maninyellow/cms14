package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.DasDeviceStatus;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.DeviceStatusVO;

/**
 * DasDeviceStatusDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:52:02
 */
public interface DasDeviceStatusDAO extends BaseDAO<DasDeviceStatus, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的DeviceStatus列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 上午11:34:30
	 */
	public void batchInsert(List<DasDeviceStatus> list)
			throws BusinessException;

	/**
	 * 设备状态统计
	 * 
	 * @param beginTime
	 *            设备状态采集开始时间
	 * @param endTime
	 *            设备状态采集结束时间
	 * @param type
	 *            设备类型
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @param start
	 *            分页起始行号
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-20 上午10:49:35
	 */
	public List<DeviceStatusVO> statDeviceStatus(Timestamp beginTime,
			Timestamp endTime, Integer type, String sns[],
			String organSN, int start, int limit);

	/**
	 * 设备状态统计记录总数量，分页需要
	 * 
	 * @param beginTime
	 *            设备状态采集开始时间
	 * @param endTime
	 *            设备状态采集结束时间
	 * @param type
	 *            设备类型
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @return 满足条件的记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-20 下午2:47:13
	 */
	public int countDeviceStatus(Timestamp beginTime, Timestamp endTime,
			Integer type, String sns[], String organSN);

	/**
	 * 条件统计设备状态数量
	 * 
	 * @param standardNumber
	 *            设备sn
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param type
	 *            设备类型
	 * @return 设备状态数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:12:30
	 */
	public int countTotalDeviceStatus(String sns[],
			Timestamp beginTime, Timestamp endTime, Integer type);

	/**
	 * 统计设备状态
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param type
	 *            类型
	 * @param standardNumber
	 *            设备sn
	 * @param organSN
	 *            机构sn
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return List<DeviceStatusVO> 设备状态列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:08:28
	 */
	public List<DeviceStatusVO> deviceStatusStat(Timestamp beginTime,
			Timestamp endTime, Integer type, String sns[],
			String organSN, Integer startIndex, Integer limit);
}
