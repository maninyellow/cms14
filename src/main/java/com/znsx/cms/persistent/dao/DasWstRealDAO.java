package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasCmsReal;
import com.znsx.cms.persistent.model.DasWstReal;

/**
 * DasWstRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:14:47
 */
public interface DasWstRealDAO extends BaseDAO<DasWstReal, String> {

	/**
	 * 
	 * 根据sn删除实时表数据
	 * 
	 * @param sn
	 *            气象检测器标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:07:13
	 */
	public void deleteBySN(String sn);

	/**
	 * 
	 * 批量插入实时数据
	 * 
	 * @param wstList
	 *            气象检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:08:00
	 */
	public void batchInsert(List<DasWstReal> wstList);

}
