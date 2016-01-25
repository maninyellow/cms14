package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Pts;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 协转服务器数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:53:29
 */
public interface PtsDAO extends BaseDAO<Pts, String> {
	/**
	 * 根据standardNumber查询PTS
	 * 
	 * @param standardNumber
	 *            PTS标准号
	 * @return PTS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 上午10:07:07
	 */
	public Pts getByStandardNumber(String standardNumber)
			throws BusinessException;

	/**
	 * 
	 * 根据名称查询pts列表
	 * 
	 * @param name
	 *            pts名称
	 *            
	 * @return pts列表
	 * @author wangbinyu <p />
	 * Create at 2013 下午2:51:15
	 */
	public List<Pts> findPtsByName(String name);
}
