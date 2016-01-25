package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.ViDetector;

/**
 * ViDetectorDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午9:34:09
 */
public interface ViDetectorDAO extends BaseDAO<ViDetector, String> {

	/**
	 * 删除能见度仪和角色关联
	 * 
	 * @param id
	 *            能见度仪id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:06:48
	 */
	public void deleteRoleViDetectorPermission(String id);

	/**
	 * 根据条件统计能见度仪数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:30:51
	 */
	public Integer countViDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 根据条件查询能见度仪列表
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
	 * @return 能见度仪列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:43:05
	 */
	public List<ViDetector> listViDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 根据机构id查询能见度仪列表
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 能见度仪列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:22:39
	 */
	public Map<String, ViDetector> mapVDByOrganIds(String[] organIds);

	/**
	 * 根据机构id数组查询能见度仪机构数
	 * 
	 * @param organIds
	 *            机构id数组
	 * @return 能见度仪机构数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:22:43
	 */
	public String[] countVID(String[] organIds);

	/**
	 * 根据机构id统计能见度仪数量
	 * 
	 * @param organ
	 *            机构id
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:56:07
	 */
	public int countByOrganId(String organ);

	/**
	 * 查询指定数采服务器下方的VID检测器列表
	 * 
	 * @param dasId
	 *            数采服务器ID
	 * @return 数采服务器下方的VID检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-9 下午3:16:54
	 */
	public List<ViDetector> listByDAS(String dasId);

}
