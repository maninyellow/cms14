package com.znsx.cms.service.iface;

import java.util.List;

import com.znsx.cms.service.model.RoadDeviceStatusVO;
import com.znsx.cms.service.model.StatCameraStatusVO;
import com.znsx.cms.service.model.StatCoviVO;
import com.znsx.cms.service.model.StatLoliVO;
import com.znsx.cms.service.model.StatNoVO;
import com.znsx.cms.service.model.StatRsdVO;
import com.znsx.cms.service.model.StatVdVO;
import com.znsx.cms.service.model.StatWstVO;
import com.znsx.cms.service.model.TunnelDeviceStatusVO;

/**
 * 统计类业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-8 上午11:09:15
 */
public interface StatManager extends BaseManager {
	/**
	 * 道路设备实时状态查询
	 * 
	 * @param organSn
	 *            机构SN
	 * @param name
	 *            设备名称条件，模糊查询
	 * @param type
	 *            设备类型:参见{@link com.znsx.cms.service.common.TypeDefinition}
	 *            中的DEVICE_TYPE类型
	 * @param status
	 *            工作状态：0-正常，1-异常，2-手动控制（不能通过平台进行控制的状态），9-报警
	 * @param commStatus
	 *            通信状态：0-正常，1-异常
	 * @return 满足条件的所有道路设备实时状态列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-8 上午11:23:48
	 */
	public List<RoadDeviceStatusVO> listRoadDeviceStatus(String organSn,
			String name, Integer type, Integer status, Integer commStatus);

	/**
	 * 隧道和桥梁设备实时状态查询
	 * 
	 * @param organSn
	 *            机构SN
	 * @param name
	 *            设备名称条件，模糊查询
	 * @param type
	 *            设备类型:参见{@link com.znsx.cms.service.common.TypeDefinition}
	 *            中的DEVICE_TYPE类型
	 * @param status
	 *            工作状态：0-正常，1-异常，2-手动控制（不能通过平台进行控制的状态），9-报警
	 * @param commStatus
	 *            通信状态：0-正常，1-异常
	 * @return 满足条件的所有隧道和桥梁设备实时状态列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-9 上午10:08:11
	 */
	public List<TunnelDeviceStatusVO> listTunnelDeviceStatus(String organSn,
			String name, Integer type, Integer status, Integer commStatus);

	/**
	 * 摄像头实时状态查询
	 * 
	 * @param organSn
	 *            机构SN
	 * @param name
	 *            设备名称条件，模糊查询
	 * @param commStatus
	 *            通信状态：0-在线，1-不在线
	 * @return 摄像头实时状态
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-9 下午3:16:41
	 */
	public List<StatCameraStatusVO> listCameraStatus(String organSn,
			String name, Integer commStatus);

	/**
	 * 按小时统计车检器采集数据
	 * 
	 * @param sn
	 *            车检器
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按小时统计车检器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-14 下午4:22:03
	 */
	public List<StatVdVO> statVdByHour(String sn, Long beginTime, Long endTime,
			int start, int limit);

	/**
	 * 统计满足条件的车检器采集数据记录数
	 * 
	 * @param sn
	 *            车检器
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的车检器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-14 下午4:24:47
	 */
	public int countVdByHour(String sn, Long beginTime, Long endTime);

	/**
	 * 按天统计车检器采集数据
	 * 
	 * @param sn
	 *            车检器
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按天统计车检器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-20 下午2:36:35
	 */
	public List<StatVdVO> statVdByDay(String sn, Long beginTime, Long endTime,
			int start, int limit);

	/**
	 * 统计满足条件的车检器采集数据记录数
	 * 
	 * @param sn
	 *            车检器
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的车检器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-20 下午2:37:26
	 */
	public int countVdByDay(String sn, Long beginTime, Long endTime);

	/**
	 * 按月统计车检器采集数据
	 * 
	 * @param sn
	 *            车检器
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按月统计车检器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-20 下午2:38:08
	 */
	public List<StatVdVO> statVdByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * 统计满足条件的车检器采集数据记录数
	 * 
	 * @param sn
	 *            车检器
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的车检器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-20 下午2:37:26
	 */
	public int countVdByMonth(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            气象检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按小时统计气象检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-26 下午1:45:32
	 */
	public List<StatWstVO> statWstByHour(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            气象检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的气象检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-26 下午1:45:37
	 */
	public int countWstByHour(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            气象检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按日统计气象检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-26 下午2:29:35
	 */
	public List<StatWstVO> statWstByDay(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            气象检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的气象检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-26 下午2:30:22
	 */
	public int countWstByDay(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            气象检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按月统计气象检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-26 下午3:07:53
	 */
	public List<StatWstVO> statWstByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            气象检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的气象检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-26 下午3:08:25
	 */
	public int countWstByMonth(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按小时统计检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-6 下午2:07:28
	 */
	public List<StatRsdVO> statRsdByHour(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-6 下午2:07:33
	 */
	public int countRsdByHour(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按日统计检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-6 下午2:09:29
	 */
	public List<StatRsdVO> statRsdByDay(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-6 下午2:09:51
	 */
	public int countRsdByDay(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按月统计检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-6 下午2:10:14
	 */
	public List<StatRsdVO> statRsdByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-6 下午2:09:59
	 */
	public int countRsdByMonth(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按小时统计检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-8 下午2:07:28
	 */
	public List<StatCoviVO> statCoviByHour(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-8 下午2:07:33
	 */
	public int countCoviByHour(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按日统计检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-8 下午2:09:29
	 */
	public List<StatCoviVO> statCoviByDay(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-8 下午2:09:51
	 */
	public int countCoviByDay(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按月统计检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-8 下午2:10:14
	 */
	public List<StatCoviVO> statCoviByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-6 下午2:09:59
	 */
	public int countCoviByMonth(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按小时统计检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 下午2:07:28
	 */
	public List<StatLoliVO> statLoliByHour(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 下午2:07:33
	 */
	public int countLoliByHour(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按日统计检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 下午2:09:29
	 */
	public List<StatLoliVO> statLoliByDay(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 下午2:09:51
	 */
	public int countLoliByDay(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按月统计检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 下午2:10:14
	 */
	public List<StatLoliVO> statLoliByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 下午2:09:59
	 */
	public int countLoliByMonth(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午4:30:03
	 */
	public int countNoByHour(String sn, Long beginTime, Long endTime);

	/**
	 * 按小时统计氮氧化物采集数据
	 * 
	 * @param sn
	 *            氮氧化物sn
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按小时统计氮氧化物采集数据
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午4:48:23
	 */
	public List<StatNoVO> statNoByHour(String sn, Long beginTime, Long endTime,
			Integer start, Integer limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午4:52:14
	 */
	public int countNoByDay(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按日统计检测器采集数据
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午4:55:50
	 */
	public List<StatNoVO> statNoByDay(String sn, Long beginTime, Long endTime,
			Integer start, Integer limit);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的检测器采集数据记录数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午4:59:17
	 */
	public int countNoByMonth(String sn, Long beginTime, Long endTime);

	/**
	 * @param sn
	 *            检测器SN
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 按月统计检测器采集数据
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:16:34
	 */
	public List<StatNoVO> statNoByMonth(String sn, Long beginTime,
			Long endTime, Integer start, Integer limit);
}
