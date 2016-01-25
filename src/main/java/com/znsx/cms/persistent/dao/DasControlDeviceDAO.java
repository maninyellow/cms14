package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasControlDevice;
import com.znsx.cms.service.exception.BusinessException;

/**
 * DasControlDeviceDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:50:32
 */
public interface DasControlDeviceDAO extends BaseDAO<DasControlDevice, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的CD列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 上午11:34:30
	 */
	public void batchInsert(List<DasControlDevice> list)
			throws BusinessException;

	/**
	 * 查询指定sn列表的CD最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午3:40:08
	 */
	public List<DasControlDevice> listCdInfo(List<String> sns);
}
