package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.RoadDetector;

/**
 * RoadDetectorDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午9:49:31
 */
public interface RoadDetectorDAO extends BaseDAO<RoadDetector, String> {
	/**
	 * 删除路面检测器和角色关系
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:04:03
	 */
	public void deleteRoleRoadDetectorPermission(String id);

	/**
	 * 统计路面检测器数量
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
	 *         Create at 2014 上午11:24:15
	 */
	public Integer countRoadDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 条件查询路面检测器列表
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
	 *         Create at 2014 上午11:28:38
	 */
	public List<RoadDetector> listRoadDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 根据机构id查询路面检测器列表
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 路面检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:43:21
	 */
	public Map<String, RoadDetector> mapRDByOrganIds(String[] organIds);

	/**
	 * 根据机构id数组查询路面检测器机构数
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 路面检测器机构数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:27:58
	 */
	public String[] countRD(String[] organIds);

	/**
	 * 根据机构id统计路面检测器数量
	 * 
	 * @param organ
	 *            机构id
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:58:32
	 */
	public int countByOrganId(String organ);

	/**
	 * 查询指定数采服务器下方的RD检测器列表
	 * 
	 * @param dasId
	 *            数采服务器ID
	 * @return 数采服务器下方的RD检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-9 下午3:17:22
	 */
	public List<RoadDetector> listByDAS(String dasId);

}
