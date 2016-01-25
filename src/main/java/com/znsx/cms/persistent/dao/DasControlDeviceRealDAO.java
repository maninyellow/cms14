package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Set;

import com.znsx.cms.persistent.model.DasControlDeviceReal;
import com.znsx.cms.service.model.TunnelDeviceStatusVO;

/**
 * DasControlDeviceRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:08:01
 */
public interface DasControlDeviceRealDAO extends
		BaseDAO<DasControlDeviceReal, String> {

	/**
	 * 
	 * 批量插入控制设备实时数据表
	 * 
	 * @param cdList
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:59:03
	 */
	public void batchInsert(List<DasControlDeviceReal> cdList);

	/**
	 * 
	 * 删除控制设备实时表
	 * 
	 * @param table
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:17:57
	 */
	public void deleteAll(String table);

	/**
	 * 
	 * 根据sn删除实时表数据
	 * 
	 * @param sn
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:47:44
	 */
	public void deleteBySN(String sn);

	/**
	 * 查询指定SN集合的实时采集数据
	 * 
	 * @param sns
	 *            设备SN集合
	 * @return 实时采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-9 上午11:09:22
	 */
	public List<TunnelDeviceStatusVO> listTunnelDeviceStatusNoTransaction(
			Set<String> sns);

}
