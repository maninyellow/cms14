package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Policy;

/**
 * 策略数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午3:22:10
 */
public interface PolicyDAO extends BaseDAO<Policy, String> {
	/**
	 * 查询机构下的策略列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param type
	 *            策略类型，参见
	 *            {@link com.znsx.cms.service.common.TypeDefinition#POLICY_TYPE_LIGHT}
	 *            {@link com.znsx.cms.service.common.TypeDefinition#POLICY_TYPE_TIME}
	 * @return 策略列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 下午2:10:47
	 */
	public List<Policy> listByOrgan(String organId, short type);
}
