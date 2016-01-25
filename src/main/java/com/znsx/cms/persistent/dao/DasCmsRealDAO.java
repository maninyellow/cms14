package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasCmsReal;

/**
 * DasCmsRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:05:47
 */
public interface DasCmsRealDAO extends BaseDAO<DasCmsReal, String> {

	/**
	 * 
	 * 删除实时数据
	 * 
	 * @param table
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:44:28
	 */
	public void deleteAll(String table);

	/**
	 * 
	 * 批量插入
	 * 
	 * @param cmsList
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:47:04
	 */
	public void batchInsert(List<DasCmsReal> cmsList);

	/**
	 * 
	 * 根据sn删除情报板实时表
	 * 
	 * @param standardNumber
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:32:15
	 */
	public void deleteBySN(String standardNumber);

	/**
	 * 
	 * 根据ids数组删除cms实时表
	 * 
	 * @param cmsIds
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午6:00:12
	 */
	public void deleteCms(String[] cmsIds);

}
