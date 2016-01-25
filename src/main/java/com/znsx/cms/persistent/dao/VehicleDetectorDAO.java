package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.SubVehicleDetector;
import com.znsx.cms.persistent.model.SyncVehicleDetector;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.service.model.VdStatByDayVO;

/**
 * 车检器数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-20 上午9:14:04
 */
public interface VehicleDetectorDAO extends BaseDAO<VehicleDetector, String> {
	/**
	 * 返回以车检器ID为键，车检器对象为值的Map映射表
	 * 
	 * @param organIds
	 *            机构ID数组
	 * @return 机构ID列表下的所有车检器键值对
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-20 上午11:05:08
	 */
	public Map<String, VehicleDetector> mapVDByOrganIds(String[] organIds);

	/**
	 * 
	 * 根据条件查询车辆检测器总计数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:03:57
	 */
	public Integer countVehicleDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据条件查询车辆检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 车辆检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:10:58
	 */
	public List<VehicleDetector> listVehicleDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit);

	/**
	 * 查询指定数采服务器下方的车检器列表
	 * 
	 * @param dasId
	 *            数采服务器ID
	 * @return 数采服务器下方的车检器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-11 下午2:45:27
	 */
	public List<VehicleDetector> listByDAS(String dasId);

	/**
	 * 根据SN数组查找车检器对象列表
	 * 
	 * @param sns
	 *            SN数组
	 * @return 返回以车检器SN为键，车检器对象为值的Map映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-17 下午4:06:08
	 */
	public Map<String, VehicleDetector> mapVDBySNs(String[] sns);

	/**
	 * 
	 * 删除角色和检测器的关系
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:35:48
	 */
	public void deleteRoleVDPermission(String id);

	/**
	 * 车检器信息查询
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @param start
	 *            开始行号，分页需要
	 * @param limit
	 *            要查询的记录数，分页需要
	 * @return 满足条件的车检器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-15 下午3:11:02
	 */
	public List<VehicleDetector> vdInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的车检器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的车检器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-15 下午3:45:14
	 */
	public Integer countVdInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

	/**
	 * 
	 * 根据organId统计车辆检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @return 检测器数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:17:48
	 */
	public int countByOrganId(String id);

	/**
	 * 
	 * 根据机构id数组查询车辆检测器
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 车辆检测器
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:11:59
	 */
	public String[] countVD(String[] organIds);

	/**
	 * 查询路段上指定方向的车检器
	 * 
	 * @param organId
	 *            路段ID
	 * @param navigation
	 *            车行方向
	 * @return 车检器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-30 下午4:19:15
	 */
	public List<VehicleDetector> listRoadVd(String organId, String navigation);

	/**
	 * 根据车检器组id删除车检器
	 * 
	 * @param id
	 *            车检器组id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:54:27
	 */
	public void deleteSubVehicleDetector(String id);

	/**
	 * 获取所有的车检器通道
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-9-24 上午10:13:54
	 */
	public List<SubVehicleDetector> listSubVehicleDetector();

	/**
	 * 名称条件查询车检器
	 * 
	 * @param organs
	 *            机构以及子机构
	 * @param name
	 *            车检器名称
	 * @return 车检器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:50:18
	 */
	public List<VehicleDetector> listVd(String organs[], String name);

	/**
	 * 按月统计车检器采集数据
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param sn
	 *            标准号集合
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 采集数据集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:51:43
	 */
	public List<SyncVehicleDetector> vdStatByMonth(String beginTime,
			String endTime, String[] sn, Integer startIndex, Integer limit);

	/**
	 * 按月统计车检器采集数量
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param sn
	 *            标准号集合
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:51:07
	 */
	public int vdStatByMonthTotal(String beginTime, String endTime, String[] sn);

	/**
	 * 按年统计车检器采集数据
	 * 
	 * @param beginTimeS
	 *            开始时间
	 * @param endTimeS
	 *            结束时间
	 * @param sn
	 *            标准号集合
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 车检器采集数据
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:18:22
	 */
	public List<VdStatByDayVO> vdStatByYear(String beginTimeS, String endTimeS,
			String[] sn, Integer startIndex, Integer limit);

	/**
	 * 按年统计车检器数量
	 * 
	 * @param beginTimeS
	 *            开始时间
	 * @param endTimeS
	 *            结束时间
	 * @param sn
	 *            标准号
	 * @return 车检器数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:26:29
	 */
	public int vdStatByYearTotal(String beginTimeS, String endTimeS, String[] sn);

	/**
	 * 根据机构或者名称查询采集中间介的车检器
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @return 采集中间介采集车检器列表
	 */
	public List<VehicleDetector> listSubVd(String[] organs, String name);
}
