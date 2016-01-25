package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.WareHouse;

/**
 * WareHouseDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午12:01:14
 */
public interface WareHouseDAO extends BaseDAO<WareHouse, String> {

	/**
	 * 根据机构id数组查询物资仓库
	 * 
	 * @param organs
	 *            机构id数组
	 * @return 物资仓库列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:41:28
	 */
	public List<WareHouse> listWareHouse(String[] organs);

	/**
	 * 根据机构id查询仓库列表
	 * 
	 * @param organIds
	 *            机构id数组
	 * @return 仓库列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:33:58
	 */
	public Map<String, WareHouse> mapWHByOrganIds(String[] organIds);

}
