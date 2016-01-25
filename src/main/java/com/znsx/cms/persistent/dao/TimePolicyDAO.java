package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.TimePolicy;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.TimePolicyItemVO;

/**
 * TimePolicyDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午4:04:08
 */
public interface TimePolicyDAO extends BaseDAO<TimePolicy, String> {
	/**
	 * 删除定时策略的策略执行计划
	 * 
	 * @param timePolicyId
	 *            定时策略ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 下午3:57:52
	 */
	public void deleteByTimePolicy(String timePolicyId)
			throws BusinessException;

	/**
	 * 查询定时策略的策略执行计划列表
	 * 
	 * @param timePolicyId
	 *            定时策略ID
	 * @return 定时策略的策略执行计划列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-28 上午10:28:07
	 */
	public List<TimePolicyItemVO> listByTimePolicy(String timePolicyId);
}
