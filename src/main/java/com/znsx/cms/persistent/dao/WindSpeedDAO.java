package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.WindSpeed;

/**
 * 风速风向检测器数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-20 上午9:20:51
 */
public interface WindSpeedDAO extends BaseDAO<WindSpeed, String> {

	/**
	 * 返回以风速风向检测器ID为键，风速风向检测器对象为值的Map映射表
	 * 
	 * @param organIds
	 *            机构ID数组
	 * @return 机构ID列表下的所有风速风向检测器键值对
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-20 上午11:05:08
	 */
	public Map<String, WindSpeed> mapWSByOrganIds(String[] organIds);

	/**
	 * 
	 * 统计风速风向检测器总计数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 检测器总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:56:54
	 */
	public Integer countWindSpeed(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据条件查询风向风速检测器集合
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
	 *            需要查询的条数
	 * @return 检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:09:45
	 */
	public List<WindSpeed> listWindSpeed(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 查询指定数采服务器下方的风速风向检测器列表
	 * 
	 * @param dasId
	 *            数采服务器ID
	 * @return 数采服务器下方的风速风向检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-11 下午3:05:58
	 */
	public List<WindSpeed> listByDAS(String dasId);

	/**
	 * 根据SN数组查找风速风向检测器对象列表
	 * 
	 * @param sns
	 *            SN数组
	 * @return 返回以风速风向检测器SN为键，风速风向检测器对象为值的Map映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-17 下午4:06:08
	 */
	public Map<String, WindSpeed> mapWSBySNs(String[] sns);

	/**
	 * 
	 * 删除检测器和角色的关系
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:37:26
	 */
	public void deleteRoleWSPermission(String id);

	/**
	 * 风速风向检测器信息查询
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
	 * @return 满足条件的风速风向检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:57:04
	 */
	public List<WindSpeed> wsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的风速风向检测器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的风速风向检测器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:58:18
	 */
	public Integer countWsInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

	/**
	 * 
	 * 统计机构下风速风向检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * 
	 * @return 检测器数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:29:43
	 */
	public int countByOrganId(String organId);

	/**
	 * 
	 * 根据机构id数组查询风速风向检测器
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 风速风向集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:08:20
	 */
	public String[] countWindSpeed(String[] organIds);
}
