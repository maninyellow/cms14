package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DeviceModel;

/**
 * 设备类型数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:15:50
 */
public interface DeviceModelDAO extends BaseDAO<DeviceModel, String> {

	/**
	 * 
	 * 根据厂商ID查询设备型号列表
	 * 
	 * @param manufacturerId
	 *            厂商设备ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 厂商设备型号列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:06:30
	 */
	public List<DeviceModel> listDeviceModel(String manufacturerId,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 根据厂商ID查询设备型号总数
	 * 
	 * @param manufacturerId
	 *            厂商ID
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:20:23
	 */
	public Integer deviceModeTotalCount(String manufacturerId);

}
