package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasVdReal;

/**
 * DasVdRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:13:34
 */
public interface DasVdRealDAO extends BaseDAO<DasVdReal, String> {

	/**
	 * 
	 * 根据sn删除车检器
	 * 
	 * @param sn
	 *            车检器标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:38:35
	 */
	public void deleteBySN(String sn);

	/**
	 * 
	 * 批量插入车检器实时表
	 * 
	 * @param vdList
	 *            车检器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:39:16
	 */
	public void batchInsert(List<DasVdReal> vdList);

}
