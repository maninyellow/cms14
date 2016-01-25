package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasTslReal;

/**
 * DasTslRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:12:52
 */
public interface DasTslRealDAO extends BaseDAO<DasTslReal, String> {

	/**
	 * 
	 * 根据sn删除实时数据
	 * 
	 * @param sn
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午2:32:45
	 */
	public void deleteBySN(String sn);

	/**
	 * 
	 * 批量插入实时数据
	 * 
	 * @param tslList
	 *            交通信号灯集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午2:33:12
	 */
	public void batchInsert(List<DasTslReal> tslList);

}
