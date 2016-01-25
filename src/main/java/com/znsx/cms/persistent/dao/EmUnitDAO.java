package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.EmUnit;

/**
 * EmUnitDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-30 下午4:42:32
 */
public interface EmUnitDAO extends BaseDAO<EmUnit, String> {

	/**
	 * 
	 * 根据条件查询应急单位列表
	 * 
	 * @param name
	 *            应急单位名称
	 * @param organId
	 *            机构id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            查询总条数
	 * @return 应急单位列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:46:03
	 */
	public List<EmUnit> listUnit(String name, String organId,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 统计应急单位数量
	 * 
	 * @param name
	 *            应急单位名称
	 * @param organId
	 *            机构id
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:50:10
	 */
	public Integer countTotalUnit(String name, String organId);

	/**
	 * 查询应急单位
	 * 
	 * @param type
	 *            单位类型
	 * @param gisId
	 *            gisId
	 * @return 应急单位
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:57:53
	 */
	public EmUnit getUnit(String type, String gisId);

}
