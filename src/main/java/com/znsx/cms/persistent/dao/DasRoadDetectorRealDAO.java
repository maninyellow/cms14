package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasRoadDetectorReal;

/**
 * DasRoadDetectorRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午10:04:44
 */
public interface DasRoadDetectorRealDAO extends
		BaseDAO<DasRoadDetectorReal, String> {

	/**
	 * 
	 * 根据sn删除路面检测器
	 * 
	 * @param sn
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:22:35
	 */
	public void deleteBySN(String sn);

	/**
	 * 
	 * 批量插入实时表数据
	 * 
	 * @param rdList
	 *            路面检测器
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:23:11
	 */
	public void batchInsert(List<DasRoadDetectorReal> rdList);

}
