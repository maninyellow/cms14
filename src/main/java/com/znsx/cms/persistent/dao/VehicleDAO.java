package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Vehicle;

/**
 * VehicleDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:54:18
 */
public interface VehicleDAO extends BaseDAO<Vehicle, String> {

	/**
	 * 
	 * 统计车辆数量
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param abilityType
	 *            功能类型
	 * @param name
	 *            车辆名称
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:34:05
	 */
	public Integer countTotalVehilce(String unitId, String abilityType,
			String name);

	/**
	 * 
	 * 条件查询车辆列表
	 * 
	 * @param unitId
	 *               应急单位id
	 * @param abilityType
	 *               功能类型
	 * @param name 
	 *               车辆名称
	 * @param startIndex
	 *               开始查询条数
	 * @param limit
	 *               需要查询条数
	 * @return  List<Vehicle>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:45:59
	 */
	public List<Vehicle> listVehicle(String unitId, String abilityType,
			String name, Integer startIndex, Integer limit);

}
