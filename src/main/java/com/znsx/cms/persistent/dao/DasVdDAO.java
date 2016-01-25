package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.DasVd;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.OrganVDVO;
import com.znsx.cms.service.model.OrganVehicleDetectorTopVO;
import com.znsx.cms.service.model.VdStatByDayVO;
import com.znsx.cms.service.model.VdVO;
import com.znsx.cms.service.model.VehicleDetectorTotalVO;
import com.znsx.cms.service.model.VehicleDetectorVO;

/**
 * DasVdDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:53:30
 */
public interface DasVdDAO extends BaseDAO<DasVd, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的VD列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 上午11:34:30
	 */
	public void batchInsert(List<DasVd> list) throws BusinessException;

	/**
	 * 查询指定sn列表的VD最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午3:40:08
	 */
	public List<DasVd> listVdInfo(List<String> sns);

	/**
	 * 车检器统计
	 * 
	 * @param beginTime
	 *            统计开始时间
	 * @param endTime
	 *            统计结束时间
	 * @param scope
	 *            统计精度，day-天，hour-小时
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
	 *         Create at 2014-5-27 上午8:58:52
	 */
	public List<VdVO> statVD(Timestamp beginTime, Timestamp endTime,
			String scope, String sns[], String organSN, int start,
			int limit);

	/**
	 * 车检器统计记录总数量，分页需要
	 * 
	 * @param beginTime
	 *            统计开始时间
	 * @param endTime
	 *            统计结束时间
	 * @param scope
	 *            统计精度，day-天，hour-小时
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @return 满足条件的记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-27 上午8:59:40
	 */
	public int countVD(Timestamp beginTime, Timestamp endTime, String scope,
			String sns[], String organSN);

	/**
	 * 统计车辆检测器数据
	 * 
	 * @param id
	 *            车辆检测器id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return VehicleDetectorVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:43:33
	 */
	public VehicleDetectorVO statVehicleDetector(String id,
			Timestamp beginTime, Timestamp endTime);

	/**
	 * 获取车检器总流量数据
	 * 
	 * @param id
	 *            车检器id
	 * @return Object
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:02:28
	 */
	public Object statVehicleDetectorTotal(String id);

	/**
	 * 获取车检器昨日总流量
	 * 
	 * @param organId
	 *            机构id
	 * @param yestodayBegin
	 *            昨日开始时间
	 * @param yestodayEnd
	 *            昨日结束时间
	 * @return 车检器昨日总流量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:16:16
	 */
	public VehicleDetectorTotalVO statOrganYesTodayTotalVD(String organId,
			Timestamp yestodayBegin, Timestamp yestodayEnd);

	/**
	 * 根据机构获取车检器总流量
	 * 
	 * @param organId
	 *            机构id
	 * @return 车检器总流量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:31:24
	 */
	public VehicleDetectorTotalVO statOrganTotalVD(String organId);

	/**
	 * 根据开始时间和结束时间统计前十机构下的车检器流量 // *
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 前十机构下车检器流量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:23:42
	 */
	public List<OrganVehicleDetectorTopVO> trafficFlowTop(Timestamp beginTime,
			Timestamp endTime);

	/**
	 * 统计路段上车流总量
	 * 
	 * @param organId
	 *            机构id
	 * @return 总流量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:29:43
	 */
	public Object roadTrafficFlowTotal(String organId);

	/**
	 * 统计路段前5分钟上下行流量
	 * 
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return OrganVDVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:32:09
	 */
	public OrganVDVO roadTrafficFlow(String organId, Timestamp beginTime,
			Timestamp endTime);

	/**
	 * 统计车检器数据,精确到小时
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param sn
	 *            设备sn数组
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 数据设备统计信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:18:47
	 */
	public List<VdStatByDayVO> vdStatByHour(Timestamp beginTime,
			Timestamp endTime, String[] sn, Integer startIndex, Integer limit);

	/**
	 * 按日统计车检器数量
	 * 
	 * @param sn
	 *            标准号数组
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param name
	 *            名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:59:13
	 */
	public int vdStatByHourTotal(String[] sn, Timestamp beginTime,
			Timestamp endTime, String name);

	/**
	 * 每个月定时统计所有车检器
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 统计车检器采集结果
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:33:35
	 */
	public List<VdStatByDayVO> vdStatByMonth(Timestamp beginTime,
			Timestamp endTime);

	/**
	 * 统计车检器数据,精确到天
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param sn
	 *            设备sn数组
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         2014-12-20 下午4:13:13
	 */
	public List<VdStatByDayVO> vdStatByDay(Timestamp beginTime,
			Timestamp endTime, String[] sn, Integer startIndex, Integer limit);

	/**
	 * 统计给定任意时间段内的车检器数据总量
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param sn
	 *            设备sn数组
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return
	 */
	public List<VdStatByDayVO> vdStat(Timestamp beginTime, Timestamp endTime,
			String[] sn, Integer startIndex, Integer limit);

	/**
	 * 统计时间范围内的车检器采集数据条数
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 时间范围内的车检器采集数据条数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-13 上午11:23:34
	 */
	public int countVd(Timestamp begin, Timestamp end);

	/**
	 * 查询时间范围内的车检器采集数据
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 时间范围内的车检器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-13 上午11:25:33
	 */
	public List<DasVd> listDasVd(Timestamp begin, Timestamp end, int start,
			int limit);
}
