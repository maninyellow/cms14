package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.TmDevice;

/**
 * TmDeviceDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-31 上午11:52:23
 */
public interface TmDeviceDAO extends BaseDAO<TmDevice, String> {

	/**
	 * 
	 * 根据条件查询gps设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-2 下午5:58:03
	 */
	public Integer countGPSDevice(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据条件查询gps设备列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-2 下午6:04:36
	 */
	public List<TmDevice> listGPSDevice(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 查询给定机构列表下的满足条件的所有数据设备
	 * 
	 * @param organIds
	 *            机构ID列表
	 * @param name
	 *            设备名称，模糊查询
	 * @param type
	 *            设备类型
	 * @return 设备SN为key,设备对象为value的映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-8 下午1:51:45
	 */
	public Map<String, TmDevice> mapDeviceByOrgansNoTransaction(
			List<String> organIds, String name, String type);
}
