package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Das;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 中心数据采集服务器数据库接口类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:25:49
 */
public interface DasDAO extends BaseDAO<Das, String> {

	/**
	 * 
	 * 根据名称查询数据采集服务器
	 * 
	 * @param name
	 *            服务器名称
	 * @return 数采服务器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:25:54
	 */
	public List<Das> findDasByName(String name);

	/**
	 * 根据standardNumber查询DAS
	 * 
	 * @param standardNumber
	 *            DAS标准号
	 * @return DAS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-11 上午10:12:31
	 */
	public Das getByStandardNumber(String standardNumber)
			throws BusinessException;

}
