package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.SubVehicleDetector;

/**
 * SubVehicleDetectorDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:43:21
 */
public interface SubVehicleDetectorDAO extends
		BaseDAO<SubVehicleDetector, String> {

	/**
	 * 统计子车检器数量
	 * 
	 * @param parentId
	 *            车检器组id
	 * @return 子车检器数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:11:18
	 */
	public Integer countSubVehicleDetector(String parentId);

	/**
	 * 条件查询子车检器列表
	 * 
	 * @param parentId
	 *            车检器组id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 子车检器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:28:32
	 */
	public List<SubVehicleDetector> listSubVehicleDetector(String parentId,
			Integer startIndex, Integer limit);

}
