package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Store;

/**
 * StoreDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午1:38:29
 */
public interface StoreDAO extends BaseDAO<Store, String> {

	/**
	 * 
	 * 物资仓库资源总数量查询
	 * 
	 * @param unitId
	 *            应急单位id
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:26:45
	 */
	public Integer countTotalStore(String unitId);

	/**
	 * 
	 * 查询物资仓库资源列表
	 * 
	 * @param unitId
	 *               单位id
	 * @param startIndex
	 *               开始查询条数
	 * @param limit
	 *               需要查询条数
	 * @return List<Store>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:31:13
	 */
	public List<Store> listStore(String unitId, Integer startIndex,
			Integer limit);

}
