package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.Srs;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 录音服务器数据库接口
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午3:49:54
 */
public interface SrsDAO extends BaseDAO<Srs, String> {
	/**
	 * 根据standardNumber查询SRS
	 * 
	 * @param standardNumber
	 *            SRS标准号
	 * @return SRS对象
	 * @throws BusinessException
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午10:07:07
	 */
	public Srs getByStandardNumber(String standardNumber)
			throws BusinessException;

	/**
	 * 
	 * 根据名称查询SRS列表
	 * 
	 * @param name
	 *            Srs名称
	 * 
	 * @return srs列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015 下午2:36:48
	 */
	public List<Srs> findSrsByName(String name);

	/**
	 * 获取所有的存储服务器对象与ID的映射表
	 * 
	 * @return key为ID，value为存储服务器对象的映射表
	 * @author sjt
	 *         <p />
	 *         Create at 2015 下午5:01:54
	 */
	public Map<String, Srs> mapByIdNoTransaction();
}
