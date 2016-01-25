package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Rms;

/**
 * RmsDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:12:02
 */
public interface RmsDAO extends BaseDAO<Rms, String> {

	/**
	 * 
	 * 根据名称查询列表
	 * 
	 * @param name
	 *            服务器名称
	 * @return 列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午3:59:43
	 */
	public List<Rms> findRmsByName(String name);

	/**
	 * 
	 * 根据sn查询RMS
	 * 
	 * @param standardNumber
	 *            标准号
	 * @return rms
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:05:41
	 */
	public Rms getByStandardNumber(String standardNumber);

}
