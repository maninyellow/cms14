package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasWsReal;

/**
 * DasWsRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:14:13
 */
public interface DasWsRealDAO extends BaseDAO<DasWsReal, String> {

	/**
	 * 
	 * 根据sn删除实时表
	 * 
	 * @param sn
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:56:26
	 */
	public void deleteBySN(String sn);

	/**
	 * 
	 * 批量插入实时表
	 * 
	 * @param wsList
	 *            风速风向集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:56:51
	 */
	public void batchInsert(List<DasWsReal> wsList);

}
