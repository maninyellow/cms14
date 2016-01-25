package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.WeatherStat;

/**
 * 气象检测器数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-20 上午9:21:30
 */
public interface WeatherStatDAO extends BaseDAO<WeatherStat, String> {
	/**
	 * 返回以气象检测器ID为键，气象检测器对象为值的Map映射表
	 * 
	 * @param organIds
	 *            机构ID数组
	 * @return 机构ID列表下的所有气象检测器键值对
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-20 上午11:09:42
	 */
	public Map<String, WeatherStat> mapWSTByOrganIds(String[] organIds);

	/**
	 * 
	 * 气象检测器总计数
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
	 *         Create at 2013 下午1:52:20
	 */
	public Integer countWeatherStat(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 查询气象检测器列表
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
	 * @return 检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:00:46
	 */
	public List<WeatherStat> listWeatherStat(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 查询指定数采服务器下方的气象检测器列表
	 * 
	 * @param dasId
	 *            数采服务器ID
	 * @return 数采服务器下方的气象检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-11 下午3:10:53
	 */
	public List<WeatherStat> listByDAS(String dasId);

	/**
	 * 根据SN数组查找气象检测器对象列表
	 * 
	 * @param sns
	 *            SN数组
	 * @return 返回以气象检测器SN为键，气象检测器对象为值的Map映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-17 下午4:06:08
	 */
	public Map<String, WeatherStat> mapWSTBySNs(String[] sns);

	/**
	 * 
	 * 删除检测器和角色的关系
	 * 
	 * @param id
	 *            检测器 id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:33:45
	 */
	public void deleteRoleWSTPermission(String id);

	/**
	 * 气象检测器信息查询
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
	 * @return 满足条件的气象检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:30:10
	 */
	public List<WeatherStat> wstInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的气象检测器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的气象检测器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:31:25
	 */
	public Integer countWstInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

	/**
	 * 
	 * 根据机构id统计气象检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @return 气象检测器数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:24:02
	 */
	public int countByOrganId(String id);

	/**
	 * 
	 * 根据机构id数组查询气象检测器
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 气象检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:14:37
	 */
	public String[] countWST(String[] organIds);

}
