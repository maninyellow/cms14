package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.UrgentPhone;

/**
 * UrgentPhoneDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:44:55
 */
public interface UrgentPhoneDAO extends BaseDAO<UrgentPhone, String> {

	/**
	 * 删除角色绑定紧急电话
	 * 
	 * @param id
	 *            紧急电话id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:42:56
	 */
	public void deleteRoleUPPermission(String id);

	/**
	 * 统计紧急电话数量
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
	 *         Create at 2014 上午9:23:03
	 */
	public Integer countUrgentPhone(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 查询紧急电话列表
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
	 * @return 紧急电话列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:30:00
	 */
	public List<UrgentPhone> listUrgentPhone(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 返回以光强检测器ID为键，光强检测器对象为值的Map映射表
	 * 
	 * @param organIds
	 *            机构id数组
	 * @return map映射表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:50:45
	 */
	public Map<String, UrgentPhone> mapUPByOrganIds(String[] organIds);

	/**
	 * 根据机构id数组查询紧急电话机构数
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 紧急电话所属机构
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:40:43
	 */
	public String[] countUP(String[] organIds);

	/**
	 * 根据机构id统计紧急电话数量
	 * 
	 * @param organ
	 *            机构id
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:03:07
	 */
	public int countByOrganId(String organ);

}
