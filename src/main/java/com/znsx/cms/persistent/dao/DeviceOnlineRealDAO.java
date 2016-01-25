package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.znsx.cms.persistent.model.DeviceOnlineReal;

/**
 * DeviceOnlineRealDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-17 上午10:29:38
 */
public interface DeviceOnlineRealDAO extends BaseDAO<DeviceOnlineReal, String> {
	/**
	 * 根据SN集合查询SN为键DeviceOnlineReal对象为值得映射表
	 * 
	 * @param sns
	 *            SN集合
	 * @return SN为键DeviceOnlineReal对象为值得映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-17 上午10:56:34
	 */
	public Map<String, DeviceOnlineReal> mapBySns(Set<String> sns);

	/**
	 * 
	 * 根据设备sn查询设备在线列表
	 * 
	 * @param deviceSN
	 *            设备sn
	 * @return 在线设备列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午2:51:36
	 */
	public List<DeviceOnlineReal> listDeviceOnline(String[] deviceSN);

	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-29 上午9:39:12
	 */
	public void batchInsert(List<DeviceOnlineReal> list);

	/**
	 * 查询在线的设备SN集合
	 * 
	 * @return 在线的设备SN集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-5 上午11:12:59
	 */
	public List<String> listOnlineDeviceSn();

	/**
	 * 查询给定SN集合的摄像头状态
	 * 
	 * @param sns
	 *            SN集合
	 * @return 摄像头状态
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-9 下午4:07:33
	 */
	public List<DeviceOnlineReal> listDeviceStatusNoTransaction(Set<String> sns);
}
