package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Ens;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 中心事件服务器数据库接口类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午1:58:36
 */
public interface EnsDAO extends BaseDAO<Ens, String> {

	/**
	 * 
	 * 根据名称查询事件服务器列表
	 * 
	 * @param name
	 *            服务器名称
	 * @return 服务器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:40:23
	 */
	public List<Ens> findEnsByName(String name);

	/**
	 * 根据standardNumber查询ENS
	 * 
	 * @param standardNumber
	 *            ENS标准号
	 * @return ENS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-11 上午10:12:31
	 */
	public Ens getByStandardNumber(String standardNumber)
			throws BusinessException;

}
