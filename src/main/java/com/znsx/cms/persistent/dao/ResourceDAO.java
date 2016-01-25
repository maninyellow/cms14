package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Resource;

/**
 * 应急物资数据库接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午9:45:29
 */
public interface ResourceDAO extends BaseDAO<Resource, String> {

	/**
	 * 
	 * 应急物资总数量
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param abilityType
	 *            功能类型
	 * @param unitType
	 *            单位类型
	 * @return 物资总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:52:52
	 */
	public Integer countTotalResource(String unitId, String abilityType,
			String unitType);

	/**
	 * 
	 * 条件查询应急物资列表
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param abilityType
	 *            功能类型
	 * @param unitType
	 *            单位类型
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 应急物资列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:01:57
	 */
	public List<Resource> listResource(String unitId,String abilityType, String unitType,
			Integer startIndex, Integer limit);

}
