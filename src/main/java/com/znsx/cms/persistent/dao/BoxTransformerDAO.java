package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.BoxTransformer;

/**
 * BoxTransformerDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:19:52
 */
public interface BoxTransformerDAO extends BaseDAO<BoxTransformer, String> {

	/**
	 * 删除角色关联关系
	 * 
	 * @param id
	 *            id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:24:24
	 */
	public void deleteRoleBTPermission(String id);

	/**
	 * 根据条件查询箱变电力监控数量
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
	 *         Create at 2014 下午5:45:10
	 */
	public Integer countBoxTransformer(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 条件查询箱变电力监控列表
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
	 * @return 电力监控列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:50:05
	 */
	public List<BoxTransformer> listBoxTransformer(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 根据机构id查询箱变电力监控列表
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 箱变电力监控列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:38:53
	 */
	public Map<String, BoxTransformer> mapBTByOrganIds(String[] organIds);

	/**
	 * 根据机构id数组查询箱变电力监控
	 * 
	 * @param organIds
	 *            机构id数组
	 * @return 箱变电力监控
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:17:53
	 */
	public String[] countBT(String[] organIds);

	/**
	 * 根据机构id统箱变电力监控数量
	 * 
	 * @param organ
	 *            机构id
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:54:10
	 */
	public int countByOrganId(String organ);

}
