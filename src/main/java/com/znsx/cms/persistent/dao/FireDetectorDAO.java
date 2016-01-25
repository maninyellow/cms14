package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.FireDetector;

/**
 * 火灾检测器数据库接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:07:27
 */
public interface FireDetectorDAO extends BaseDAO<FireDetector, String> {

	/**
	 * 
	 * 根据条件查询火灾检测器总数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            火灾检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:45:46
	 */
	public Integer countFireDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据查询条件获取火灾检测器列表
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
	 *            开始条数
	 * @param limit
	 *            需要查询条数
	 * @return 火灾检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:07
	 */
	public List<FireDetector> listFireDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 返回以火灾检测器ID为键，火灾检测器对象为值的Map映射表
	 * 
	 * @param organIds
	 *            机构ID数组
	 * @return 机构ID列表下的所有火灾检测器键值对
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-21 下午2:46:48
	 */
	public Map<String, FireDetector> mapFDByOrganIds(String[] organIds);

	/**
	 * 根据SN数组查找火灾检测器对象列表
	 * 
	 * @param sns
	 *            SN数组
	 * @return 返回以火灾检测器SN为键，火灾检测器对象为值的Map映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-17 下午4:06:08
	 */
	public Map<String, FireDetector> mapFDBySNs(String[] sns);

	/**
	 * 
	 * 删除角色和火灾检测器关联关系
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:10:09
	 */
	public void deleteRoleFDPermission(String id);

	/**
	 * 
	 * 根据机构id统计火灾检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @return 火灾检测器数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:11:49
	 */
	public int countByOrganId(String organId);

	/**
	 * 
	 * 根据机构id数组查询火灾检测器
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 火灾检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:22:57
	 */
	public String[] countFD(String[] organIds);

	/**
	 * 
	 * 批量创建火灾检测器
	 * 
	 * @param fds
	 *            火灾检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-29 上午11:24:36
	 */
	public void batchInsert(List<FireDetector> fds);

	/**
	 * 
	 * 批量插入fd
	 * 
	 * @param fireDetector
	 *            fd对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午4:56:13
	 */
	public void batchInsertFd(FireDetector fireDetector);

	/**
	 * 
	 * 执行批量写入数据库操作
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午4:58:32
	 */
	public void excuteBatchFd();
}
