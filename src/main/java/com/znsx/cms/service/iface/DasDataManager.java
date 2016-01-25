package com.znsx.cms.service.iface;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.jdom.Element;

import com.znsx.cms.persistent.model.DasCovi;
import com.znsx.cms.persistent.model.DasGpsReal;
import com.znsx.cms.persistent.model.DasLoli;
import com.znsx.cms.persistent.model.DasNod;
import com.znsx.cms.persistent.model.DasRoadDetector;
import com.znsx.cms.persistent.model.DasVd;
import com.znsx.cms.persistent.model.DasWst;
import com.znsx.cms.persistent.model.SyncVehicleDetector;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.CoviVO;
import com.znsx.cms.service.model.DeviceStatusVO;
import com.znsx.cms.service.model.LoLiVO;
import com.znsx.cms.service.model.OrganVDVO;
import com.znsx.cms.service.model.OrganVehicleDetectorTopVO;
import com.znsx.cms.service.model.RoadFluxStatVO;
import com.znsx.cms.service.model.VdStatByDayVO;
import com.znsx.cms.service.model.VdVO;
import com.znsx.cms.service.model.VehicleDetectorTotalVO;
import com.znsx.cms.service.model.VehicleDetectorVO;
import com.znsx.cms.service.model.WsVO;
import com.znsx.cms.service.model.WstVO;

/**
 * 数据采集服务业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-2 下午5:32:32
 */
public interface DasDataManager extends BaseManager {

	public static final String DEVICESTATUS = "deviceStatus";
	public static final String CMS = "cms";
	public static final String CD = "cd";
	public static final String COVI = "covi";
	public static final String LOLI = "loli";
	public static final String NOD = "nod";
	public static final String VD = "vd";
	public static final String WS = "ws";
	public static final String WST = "wst";
	public static final String TSL = "tsl";
	public static final String LIL = "lil";
	public static final String REGION = "dasCache";

	/**
	 * 保存数据
	 * 
	 * @param data
	 *            要存入库的数据列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-3 下午2:55:51
	 */
	public void saveData(List<Element> data) throws BusinessException;

	// public void testBatch(List<Object[]> data);
	//
	// public void testJdbcBatch(List<Object[]> data);

	public List<Element> listDeviceInfo(List<Element> devices)
			throws BusinessException;

	/**
	 * 根据设备名称和类型查询设备编号
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param type
	 *            设备类型,可以为空，将会依次搜索每张设备表，直到找到为止
	 * @return 设备编号
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-24 下午3:04:46
	 */
	public List<String> getStandardNumberByNameAndType(String deviceName,
			Integer type) throws BusinessException;

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
	 *         Create at 2013-12-17 下午2:26:50
	 */
	public List<DeviceStatusVO> statDeviceStatus(Timestamp beginTime,
			Timestamp endTime, Integer type, String sns[],
			String organSN, int start, int limit);

	/**
	 * 完善设备状态统计的结果集，将设备和机构的信息加入到结果集中
	 * 
	 * @param list
	 *            设备状态统计的初步查询结果集
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-17 下午3:42:34
	 */
	public void completeStatDeviceStatus(List<DeviceStatusVO> list);

	/**
	 * 设备状态统计总数量，分页需要
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
	 *         Create at 2013-12-18 上午10:37:03
	 */
	public int countDeviceStatus(Timestamp beginTime, Timestamp endTime,
			Integer type, String[] sns, String organSN);

	/**
	 * 车辆监测器统计
	 * 
	 * @param beginTime
	 *            统计开始时间
	 * @param endTime
	 *            统计结束时间
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @param scope
	 *            统计精度，day-天，hour-小时
	 * @param start
	 *            分页起始行号
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-23 下午2:56:20
	 */
	public List<VdVO> statVD(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope, int start,
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
	 *         Create at 2013-12-24 下午2:59:59
	 */
	public int countVD(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope);

	/**
	 * 完善车检器统计结果
	 * 
	 * @param list
	 *            车检器统计初步结果
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-24 下午4:04:09
	 */
	public void completeStatVD(List<VdVO> list);

	/**
	 * 统计气象检测器
	 * 
	 * @param beginTime
	 *            统计开始时间
	 * @param endTime
	 *            统计结束时间
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @param scope
	 *            统计精度，day-天，hour-小时
	 * @param start
	 *            分页起始行号
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-26 下午2:31:45
	 */
	public List<WstVO> statWST(Timestamp beginTime, Timestamp endTime,
			String standardNumber, String organSN, String scope, int start,
			int limit);

	/**
	 * 气象检测器统计记录总数量，分页需要
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
	 *         Create at 2013-12-26 下午3:53:30
	 */
	public int countWST(Timestamp beginTime, Timestamp endTime,
			String standardNumber, String organSN, String scope);

	/**
	 * 完善气象检测器统计结果
	 * 
	 * @param list
	 *            气象检测器统计初步结果
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-26 下午3:55:47
	 */
	public void completeStatWST(List<WstVO> list);

	/**
	 * 统计风速风向检测器
	 * 
	 * @param beginTime
	 *            统计开始时间
	 * @param endTime
	 *            统计结束时间
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @param scope
	 *            统计精度，day-天，hour-小时
	 * @param start
	 *            分页起始行号
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-31 上午10:04:22
	 */
	public List<WsVO> statWS(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope, int start,
			int limit);

	/**
	 * 风速风向检测器统计记录总数量，分页需要
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
	 *         Create at 2013-12-31 上午10:05:20
	 */
	public int countWS(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope);

	/**
	 * 完善风速风向检测器统计结果
	 * 
	 * @param list
	 *            风速风向检测器统计初步结果
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-31 上午10:29:53
	 */
	public void completeStatWS(List<WsVO> list);

	/**
	 * 统计光强检测器
	 * 
	 * @param beginTime
	 *            统计开始时间
	 * @param endTime
	 *            统计结束时间
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @param scope
	 *            统计精度，day-天，hour-小时
	 * @param start
	 *            分页起始行号
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-2 下午2:26:58
	 */
	public List<LoLiVO> statLoLi(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope, int start,
			int limit);

	/**
	 * 光强检测器统计记录总数量，分页需要
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
	 *         Create at 2014-1-2 下午2:28:14
	 */
	public int countLoLi(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope);

	/**
	 * 完善光强检测器统计结果
	 * 
	 * @param list
	 *            光强检测器统计初步结果
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-2 下午2:29:12
	 */
	public void completeStatLoLi(List<LoLiVO> list);

	/**
	 * 统计COVI检测器
	 * 
	 * @param beginTime
	 *            统计开始时间
	 * @param endTime
	 *            统计结束时间
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @param scope
	 *            统计精度，day-天，hour-小时
	 * @param start
	 *            分页起始行号
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-9 上午11:01:43
	 */
	public List<CoviVO> statCovi(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope, int start,
			int limit);

	/**
	 * COVI检测器统计记录总数量，分页需要
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
	 *         Create at 2014-1-9 上午11:02:20
	 */
	public int countCovi(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope);

	/**
	 * 完善COVI检测器统计结果
	 * 
	 * @param list
	 *            COVI检测器统计初步结果
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-9 上午11:10:38
	 */
	public void completeStatCovi(List<CoviVO> list);

	/**
	 * 移除不用的开关量采集数据
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-10 下午3:28:51
	 */
	public void removeSwitchData();

	/**
	 * 获取资源路由信息
	 * 
	 * @param standardNumber
	 *            资源标准号
	 * @return {@code <Route LanIp="" Port="" />} 格式的资源所属服务器IP和端口
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-7 上午9:11:14
	 */
	public Element getResourceRouteInfo(String standardNumber)
			throws BusinessException;

	/**
	 * 统计车检器5分钟流量
	 * 
	 * @param id
	 *            车检器id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return VdVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:11:46
	 */
	public VehicleDetectorVO statVehicleDetector(String id,
			Timestamp beginTime, Timestamp endTime);

	/**
	 * 统计车流量总量
	 * 
	 * @param organId
	 *            机构id
	 * @return 车流量总量信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:09:54
	 */
	public VehicleDetectorTotalVO statOrganTotalVD(String organId);

	/**
	 * 统计前十机构车流量信息
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 前十机构列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:12:42
	 */
	public List<OrganVehicleDetectorTopVO> trafficFlowTop(Timestamp beginTime,
			Timestamp endTime);

	/**
	 * 根据路段统计车流量
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
	 *         Create at 2014 下午4:27:15
	 */
	public OrganVDVO roadTrafficFlow(String organId, Timestamp beginTime,
			Timestamp endTime);

	/**
	 * 统计设备状态所需总数量
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param time
	 *            时间
	 * @param type
	 *            设备类型
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:02:03
	 */
	public int countTotalDeviceStatus(String sns[], Long time,
			Integer type);

	/**
	 * 统计设备状态
	 * 
	 * @param time
	 *            选择的月份
	 * @param type
	 *            设备类型
	 * @param standardNumber
	 *            设备sn
	 * @param organSN
	 *            机构sn
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 设备状态列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:57:42
	 */
	public List<DeviceStatusVO> deviceStatusStat(Long time, Integer type,
			String sns[], String organSN, Integer startIndex,
			Integer limit);

	/**
	 * 按日查询车辆信息
	 * 
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
	 * @param name
	 *            车检器名称
	 * @param flag
	 *            日月年标志
	 * @return 车检器统计信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:18:01
	 */
	public Element vdStat(String organId, Timestamp beginTime,
			Timestamp endTime, Integer startIndex, Integer limit, String name,
			String flag);

	/**
	 * 统计车检器采集数量
	 * 
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param name
	 *            车检器名称
	 * @param flag
	 *            年月日标志
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:55:00
	 */
	public int vdStatByDayTotal(String organId, Timestamp beginTime,
			Timestamp endTime, String name, String flag);

	public void testInsertBatchVd();

	/**
	 * 
	 * 定时存入CMS所有车检器采集数据
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:00:03
	 */
	public void vdStatByMonth();

	/**
	 * 查询指定sn列表的VD最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return 指定sn列表的VD最近采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-21 上午10:26:52
	 */
	public List<DasVd> listVdInfo(List<String> sns);

	/**
	 * 统计指定时间范围内的车检器数据
	 * 
	 * @param ids
	 *            要统计的车检器ID集合
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param scope
	 *            精度：hour/day
	 * @return 统计结果
	 * @author huangbuji
	 *         <p />
	 *         2014-12-13 下午1:53:16
	 */
	public List<VdStatByDayVO> statVdByTime(String[] ids, Timestamp beginTime,
			Timestamp endTime, String scope);

	/**
	 * 查询气象监测数据列表
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organSN
	 *            机构SN
	 * @param standardNumber
	 *            设备SN，可以为空
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 气象监测数据列表
	 */
	public List<WstVO> listWSTInfo(Timestamp beginTime, Timestamp endTime,
			String organSN, String sns[], int start, int limit);

	/**
	 * 统计满足条件的气象采集数据条数
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param OrganSN
	 *            机构SN
	 * @param standardNumber
	 *            设备SN，可以为空
	 * @return 满足条件的气象采集数据条数
	 */
	public int countWSTInfo(Timestamp beginTime, Timestamp endTime,
			String organSN, String sns[]);

	public void saveData1(List<Element> data);

	public List<Element> listDeviceInfo1(List<Element> devices);

	/**
	 * 批量查入月统计数据
	 * 
	 * @param list
	 *            要插入的统计数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-21 下午2:36:16
	 */
	public void batchInsertVdSyncData(List<SyncVehicleDetector> list);

	/**
	 * 按月统计平台中的路段车流量
	 * 
	 * @return 每条路每个月1条统计数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-21 下午2:49:43
	 */
	public Collection<RoadFluxStatVO> listRoadFlux();

	public void testInsertBatchWst();

	/**
	 * 查询Gps设备当前采集信息
	 * 
	 * @return 所有的Gps设备当前采集信息
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-6-10 上午11:39:13
	 */
	public List<DasGpsReal> listGpsInfo();

	/**
	 * 
	 * 车检器排序
	 * 
	 * @param list
	 *            车检器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午2:55:45
	 * @return
	 */
	public List<VdStatByDayVO> sortVD(List<VdStatByDayVO> list);

	public void batchInsertWstData(List<DasWst> list);

	public void batchInsertRsdData(List<DasRoadDetector> list);

	public void batchInsertCoviData(List<DasCovi> list);

	public void batchInsertLoliData(List<DasLoli> list);

	public void batchInsertNoData(List<DasNod> list);

	public void testInsertBatchWs();

	public void testInsertBatchCovi();

	public void testInsertBatchNo();

	public void testInsertBatchLoli();

	public void testInsertBatchRoadDetector();

	public void testInsertBatchCmsPushLog();

}
