package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Dws;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 显示墙服务器数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:15:04
 */
public interface DwsDAO extends BaseDAO<Dws, String> {
	/**
	 * 根据standardNumber查询DWS
	 * 
	 * @param standardNumber
	 *            DWS标准号
	 * @return DWS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 上午10:07:07
	 */
	public Dws getByStandardNumber(String standardNumber)
			throws BusinessException;

	/**
	 * 
	 * 根据名称查询dws列表
	 *  
	 * @param name
	 *              dws名称
	 *              
	 * @return dws列表
	 * @author wangbinyu <p />
	 * Create at 2013 下午2:54:50
	 */
	public List<Dws> findDwsByName(String name);
}
