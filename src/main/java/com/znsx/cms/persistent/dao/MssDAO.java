package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Mss;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 流媒体服务器数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:51:53
 */
public interface MssDAO extends BaseDAO<Mss, String> {
	/**
	 * 根据standardNumber查询MSS
	 * 
	 * @param standardNumber
	 *            MSS标准号
	 * @return MSS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 上午10:07:07
	 */
	public Mss getByStandardNumber(String standardNumber)
			throws BusinessException;

	/**
	 * 
	 * 根据名称查询mss列表
	 * 
	 * @param name
	 *           mss名称
	 * @return mss列表
	 * @author wangbinyu <p />
	 * Create at 2013 下午2:47:24
	 */
	public List<Mss> findMssByName(String name);
}
