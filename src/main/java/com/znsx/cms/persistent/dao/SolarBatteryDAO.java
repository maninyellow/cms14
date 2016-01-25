package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.SolarBattery;
import com.znsx.cms.service.model.DeviceSolarVO;

/**
 * SolarBatteryDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:03:41
 */
public interface SolarBatteryDAO extends BaseDAO<SolarBattery, String> {

	/**
	 * 统计太阳能电池总计数
	 * 
	 * @param name
	 *            名称
	 * @param organId
	 *            机构id
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:31:33
	 */
	public Integer solarBatteryTotalCount(String name, String organId);

	/**
	 * 条件查询太阳能电池列表
	 * 
	 * @param name
	 *            名称
	 * @param organId
	 *            机构id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 太阳能电池列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:38:28
	 */
	public List<SolarBattery> listSolarBattery(String name, String organId,
			Integer startIndex, Integer limit);

	/**
	 * 查询设备关联的太阳能电池属性
	 * 
	 * @param organIds
	 *            机构id
	 * @return 太阳能属性列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:54:24
	 */
	public List<DeviceSolarVO> listDeviceSolar(String[] organIds);

	/**
	 * 查询设备关联的太阳能电池属性
	 * 
	 * @return 太阳能属性列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-30 上午9:46:12
	 */
	public List<DeviceSolarVO> listDeviceSolar();

}
