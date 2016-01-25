package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasLilReal;
import com.znsx.cms.persistent.model.DasTslReal;

/**
 * DasLilRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:10:16
 */
public interface DasLilRealDAO extends BaseDAO<DasLilReal, String> {

	/**
	 * 
	 * 根据sn删除实时数据
	 * 
	 * @param sn
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午2:54:50
	 */
	public void deleteBySN(String sn);

	/**
	 * 
	 * 批量插入实时数据
	 * 
	 * @param lilList
	 *            车道指示器集合
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午2:55:32
	 */
	public void batchInsert(List<DasLilReal> lilList);

}
