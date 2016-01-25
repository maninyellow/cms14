package com.znsx.cms.persistent.dao;

import java.util.Map;

import com.znsx.cms.persistent.model.SubPlatform;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 下级平台数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-13 上午10:44:56
 */
public interface SubPlatformDAO extends BaseDAO<SubPlatform, String> {
	/**
	 * 根据平台标准号查询平台
	 * 
	 * @param standardNumber
	 *            平台标准号
	 * @return 平台对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-17 下午5:23:16
	 */
	public SubPlatform getByStandardNumber(String standardNumber)
			throws BusinessException;

	/**
	 * 查询所有的外平台对象
	 * 
	 * @return 返回以平台SN为key,平台对象为value的Map映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-17 下午5:24:00
	 */
	public Map<String, SubPlatform> mapSubPlatform();
}
