package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.SolarDevice;
import com.znsx.cms.service.model.DevicePermissionVO;

/**
 * SolarDeviceDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:05:43
 */
public interface SolarDeviceDAO extends BaseDAO<SolarDevice, String> {

	/**
	 * 删除太阳能电池和设备的关联关系
	 * 
	 * @param solarId
	 *            太阳能电池
	 * @param type
	 *            设备类型
	 * @param deviceIds
	 *            设备id数组
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:37:18
	 */
	public void deleteBySolarDevice(String solarId, String type,
			String[] deviceIds);

	/**
	 * 统计太阳能电池绑定摄像头数量
	 * 
	 * @param organId
	 *            机构id
	 * @param solarId
	 *            太阳能电池id
	 * @param name
	 *            摄像头名称
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:43:19
	 */
	public int countOrganCamera(String organId, String solarId, String name);

	/**
	 * 查询太阳能电池关联摄像头
	 * 
	 * @param organId
	 *            机构id
	 * @param solarId
	 *            太阳能电池id
	 * @param name
	 *            摄像头名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 摄像头
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:46:49
	 */
	public List<DevicePermissionVO> listOrganCamera(String organId,
			String solarId, String name, int startIndex, int limit);

	/**
	 * 取消太阳能电池关联设备
	 * 
	 * @param solarId
	 *            太阳能电池id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:56:27
	 */
	public void removeSolarDevice(String solarId);

	/**
	 * 统计太阳能电池关联的车检器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param solarId
	 *            太阳能电池id
	 * @param name
	 *            车检器名称
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:22:00
	 */
	public int countOrganVD(String organId, String solarId, String name);

	/**
	 * 查询太阳能电池关联的车检器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param solarId
	 *            电池id
	 * @param name
	 *            车检器名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 车检器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:27:32
	 */
	public List<DevicePermissionVO> listOrganVD(String organId, String solarId,
			String name, int startIndex, int limit);

}
