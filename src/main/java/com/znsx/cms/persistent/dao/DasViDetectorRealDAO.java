package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasViDetectorReal;

/**
 * DasViDetectorRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午9:57:11
 */
public interface DasViDetectorRealDAO extends
		BaseDAO<DasViDetectorReal, String> {

	/**
	 * 
	 * 根据sn删除能见度仪
	 * 
	 * @param sn
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:40:31
	 */
	public void deleteBySN(String sn);

	/**
	 * 
	 * 批量插入实时数据
	 * 
	 * @param vidList
	 *            能见度仪列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:43:44
	 */
	public void batchInsert(List<DasViDetectorReal> vidList);

}
