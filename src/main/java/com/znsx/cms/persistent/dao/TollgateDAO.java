package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Tollgate;

/**
 * TollgateDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:50:16
 */
public interface TollgateDAO extends BaseDAO<Tollgate, String> {

	/**
	 * 根据条件查询收费站列表信息
	 * 
	 * @param name
	 *            收费站名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param organId
	 *            机构id
	 * @return 收费站列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:03:41
	 */
	public List<Tollgate> listTollgate(String name, Integer startIndex,
			Integer limit, String organId);

	/**
	 * 统计收费站数量
	 * 
	 * @param name
	 *            收费站名称
	 * @param organId
	 *            机构id
	 * @return 收费站数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:59:45
	 */
	public Integer tollgateTotalCount(String name, String organId);

	/**
	 * 查询给定机构ID集合下的收费站
	 * 
	 * @param organIds
	 *            机构ID集合
	 * @return 收费站列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-16 下午5:57:36
	 */
	public List<Tollgate> listTollgates(List<String> organIds);
	
	/**
	 * 根据收费站名称查询收费站
	 * @param name 收费站名称
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         2014-12-1 下午4:27:24
	 */
	public List<String> listByName(String name);
	
}
