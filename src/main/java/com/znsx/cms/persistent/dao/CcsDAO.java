package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Ccs;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 通信服务器数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:48:23
 */
public interface CcsDAO extends BaseDAO<Ccs, String> {

	/**
	 * 
	 * 查询服务器列表
	 * 
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 服务器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:39:06
	 */
	public List<Ccs> findPlatformServer(Integer startIndex, Integer limit);

	/**
	 * 
	 * 查询服务器列表总计数
	 * 
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:44:02
	 */
	public Integer platformServerTotalCount();

	/**
	 * 根据standardNumber查询CCS
	 * 
	 * @param standardNumber
	 *            CCS标准号
	 * @return CCS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:03:01
	 */
	public Ccs getByStandardNumber(String standardNumber)
			throws BusinessException;

	/**
	 * 
	 * 根据名称查询CCS列表
	 * 
	 * @param name
	 *            ccs名称
	 * 
	 * @return ccs列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:25:09
	 */
	public List<Ccs> findCcsByName(String name);

}
