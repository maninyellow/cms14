package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.BridgeDetector;

/**
 * BridgeDetectorDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:03:32
 */
public interface BridgeDetectorDAO extends BaseDAO<BridgeDetector, String> {

	/**
	 * 删除角色和桥面检测器关联
	 * 
	 * @param id
	 *            桥面检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:40:50
	 */
	public void deleteRoleBridgeDetectorPermission(String id);

	/**
	 * 统计桥面检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:04:19
	 */
	public Integer countBridgeDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 条件查询桥面检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:07:40
	 */
	public List<BridgeDetector> listBridgeDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 根据机构id查询桥面检测器列表
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 桥面检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:09:08
	 */
	public Map<String, BridgeDetector> mapBDByOrganIds(String[] organIds);

	/**
	 * 根据机构id数组查询桥面检测器机构数
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 桥面检测器所属机构
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:32:18
	 */
	public String[] countBD(String[] organIds);

	/**
	 * 根据机构id统计桥面检测器数量
	 * 
	 * @param organ
	 *            机构id
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:00:11
	 */
	public int countByOrganId(String organ);

}
