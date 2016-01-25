package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.Covi;

/**
 * 一氧化碳\能见度检测器数据库接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:19:22
 */
public interface CoviDAO extends BaseDAO<Covi, String> {

	/**
	 * 
	 * 统计一氧化碳\能见度检测数量
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
	 *         Create at 2013 下午4:05:48
	 */
	public Integer countCovi(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据条件查询一氧化碳\能见度检测器列表
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
	 * @return 一氧化碳\能见度检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:17:09
	 */
	public List<Covi> listCovi(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 返回以CO/VI检测器ID为键，CO/VI检测器对象为值的Map映射表
	 * 
	 * @param organIds
	 *            机构ID数组
	 * @return 机构ID列表下的所有CO/VI检测器键值对
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-21 下午4:52:00
	 */
	public Map<String, Covi> mapCoViByOrganIds(String[] organIds);

	/**
	 * 查询指定数采服务器下方的CO/VI检测器列表
	 * 
	 * @param dasId
	 *            数采服务器ID
	 * @return 数采服务器下方的CO/VI检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-11 下午4:01:53
	 */
	public List<Covi> listByDAS(String dasId);

	/**
	 * 根据SN数组查找CO/VI检测器对象列表
	 * 
	 * @param sns
	 *            SN数组
	 * @return 返回以CO/VI检测器SN为键，CO/VI检测器对象为值的Map映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-17 下午4:06:08
	 */
	public Map<String, Covi> mapCoviBySNs(String[] sns);

	/**
	 * 
	 * 删除检测器和角色的关联关系
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:24:29
	 */
	public void deleteRoleCoviPermission(String id);

	/**
	 * COVI检测器信息查询
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
	 * @return 满足条件的COVI检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:54:37
	 */
	public List<Covi> coviInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的COVI检测器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的COVI检测器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:55:33
	 */
	public Integer countCoviInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

	/**
	 * 
	 * 根据机构id统计covi检测器
	 * 
	 * @param organId
	 *            机构id
	 * @return covi检测器数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:38:41
	 */
	public int countByOrganId(String organId);

	/**
	 * 
	 * 根据机构id查询covi检测器
	 * 
	 * @param organIds
	 *            机构数组
	 * @return covi检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:25:23
	 */
	public String[] countCovi(String[] organIds);
}
