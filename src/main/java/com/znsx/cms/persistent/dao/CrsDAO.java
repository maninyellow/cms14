package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.Crs;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 中心存储服务器数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:49:54
 */
public interface CrsDAO extends BaseDAO<Crs, String> {
	/**
	 * 根据standardNumber查询CRS
	 * 
	 * @param standardNumber
	 *            CRS标准号
	 * @return CRS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 上午10:07:07
	 */
	public Crs getByStandardNumber(String standardNumber)
			throws BusinessException;

	/**
	 * 
	 * 根据名称查询CRS列表
	 * 
	 * @param name
	 *            crs名称
	 * 
	 * @return crs列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:36:48
	 */
	public List<Crs> findCrsByName(String name);

	/**
	 * 获取所有的存储服务器对象与ID的映射表
	 * 
	 * @return key为ID，value为存储服务器对象的映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-9 下午5:01:54
	 */
	public Map<String, Crs> mapByIdNoTransaction();
}
