package com.znsx.cms.persistent.dao;

import java.util.Collection;
import java.util.List;

import com.znsx.cms.persistent.model.DasDeviceStatusReal;
import com.znsx.cms.service.exception.BusinessException;

/**
 * DasDeviceStatusRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:09:38
 */
public interface DasDeviceStatusRealDAO extends
		BaseDAO<DasDeviceStatusReal, String> {

	/**
	 * 
	 * 删除设备状态实时表数据
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午9:56:21
	 */
	public void deleteAll(String table);

	/**
	 * 
	 * 批量插入设备状态实时数据
	 * 
	 * @param list
	 *            设备实时状态集合
	 * @throws BusinessException
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:21:42
	 */
	public void batchInsert(List<DasDeviceStatusReal> list)
			throws BusinessException;

	/**
	 * 
	 * 根据sn删除实时设备状态表数据
	 * 
	 * @param standardNumber
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:53:02
	 */
	public void deleteBySN(String standardNumber);

	/**
	 * 查询给定SN集合的设备状态信息
	 * 
	 * @param sns
	 *            设备SN集合
	 * @return 设备状态信息列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-8 下午3:52:11
	 */
	public List<DasDeviceStatusReal> listDeviceStatusRealNoTransaction(
			Collection<String> sns);
}
