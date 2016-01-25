package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.GPSDevice;
import com.znsx.cms.service.model.DevicePermissionVO;

/**
 * GPSDeviceDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午1:49:19
 */
public interface GPSDeviceDAO extends BaseDAO<GPSDevice, String> {

	/**
	 * 
	 * 删除关联关系
	 * 
	 * @param gpsId
	 *            gps设备id
	 * @param type
	 *            关联类型
	 * @param deviceIds
	 *            关联设备ids
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午1:57:11
	 */
	public void deleteByGPSDevice(String gpsId, String type, String[] deviceIds);

	/**
	 * 
	 * 查询摄像头列表
	 * 
	 * @param organId
	 *            机构id
	 * @param gpsId
	 *            gps设备id
	 * @param name
	 *            摄像头名称
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @return 摄像头列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-4 下午2:42:20
	 */
	public List<DevicePermissionVO> listOrganCameraByGPS(String organId,
			String gpsId, String name, int startIndex, int limit);

	/**
	 * 
	 * 解除gps和摄像头绑定
	 * 
	 * @param gpsId
	 *            gps设备id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-4 下午3:03:00
	 */
	public void removeGPSDevice(String gpsId);

}
